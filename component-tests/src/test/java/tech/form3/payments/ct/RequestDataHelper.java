package tech.form3.payments.ct;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Victor Oniga
 */
public class RequestDataHelper {
    // statuses
    public static final String EXPECTED_RESPONSE_SUCCESS = "json/status/success.json";
    public static final String EXPECTED_RESPONSE_ERROR_401 = "json/status/error401.json";

    // no payments
    public static final String EXPECTED_RESPONSE_NO_PAYMENTS = "json/no-payments-response.json";
    public static final String EXPECTED_RESPONSE_GET_BY_ID_ERROR = "json/get-by-id-error-response.json";

    // creates
    public static final String REQUEST_CREATE_PAYMENT1 = "json/create/payment1-request.json";
    public static final String EXPECTED_RESPONSE_CREATE_PAYMENT1 = "json/create/payment1-response.json";
    public static final String REQUEST_CREATE_PAYMENT2 = "json/create/payment2-request.json";
    public static final String EXPECTED_RESPONSE_CREATE_PAYMENT2 = "json/create/payment2-response.json";

    // updates
    public static final String REQUEST_UPDATE_PAYMENT1 = "json/update/payment1-request.json";
    public static final String EXPECTED_RESPONSE_UPDATE_PAYMENT1 = "json/update/payment1-response.json";
    public static final String REQUEST_UPDATE_PAYMENT2 = "json/update/payment2-request.json";
    public static final String EXPECTED_RESPONSE_UPDATE_PAYMENT2 = "json/update/payment2-response.json";
    public static final String EXPECTED_RESPONSE_UPDATED_PAYMENTS = "json/update/payments-response.json";

    public static String getFileFromResources(String fileName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);

        return IOUtils.toString(is);
    }
}
