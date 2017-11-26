package tech.form3.payments.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import tech.form3.payments.ejb.model.ErrorCode;
import tech.form3.payments.web.dto.OperationType;
import tech.form3.payments.web.dto.StatusDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Victor Oniga
 */
public class SecurityFilter implements Filter {
    private ObjectMapper mapper = new ObjectMapper();
    private boolean enabled;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        enabled = Boolean.parseBoolean(filterConfig.getInitParameter("enabled"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (enabled) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;

            // this is for cors
            if ("OPTIONS".equals(servletRequest.getMethod()) && "127.0.0.1".equals(servletRequest.getLocalAddr())) {
                chain.doFilter(request, response);
                return;
            }

            // get authorization token and validate it against IAM service
            String authorizationToken = servletRequest.getHeader("Authorization");
			// suppose that any non empty authorization token is valid
            if (authorizationToken != null) {
                chain.doFilter(request, response);
                return;
            }

            sendNotLoggedIn(servletResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendNotLoggedIn(HttpServletResponse servletResponse) throws IOException {
        // return 401
        StatusDTO status = new StatusDTO(OperationType.ERROR, ErrorCode.UNAUTHORIZED, "You are unauthorized to access this API");
        String json = mapper.writeValueAsString(status);

        servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        servletResponse.setContentType("application/json");
        servletResponse.getOutputStream().write(json.getBytes());
        servletResponse.getOutputStream().close();
    }

    @Override
    public void destroy() {
    }
}
