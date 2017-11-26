package tech.form3.payments.ct;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Victor Oniga
 */
public class RequestHelper {
    // requests to server with NO authorization
    public static Response executeGetPaymentsNoAuthorizationToken(WebTarget webTarget) {
        return webTarget
                .path("/payments")
                .request(MediaType.APPLICATION_JSON)
                .get();
    }

    public static Response executeGetFullDataPaymentsNoAuthorizationToken(WebTarget webTarget) {
        return webTarget
                .path("/payments/full")
                .request(MediaType.APPLICATION_JSON)
                .get();
    }

    public static Response executeGetPaymentByIdNoAuthorizationToken(WebTarget webTarget, String id) {
        return webTarget
                .path("/payments/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();
    }

    public static Response executeCreatePaymentNoAuthorizationToken(WebTarget webTarget, String requestFile) throws IOException {
        return webTarget
                .path("/payments")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(RequestDataHelper.getFileFromResources(requestFile)));
    }

    public static Response executeUpdatePaymentNoAuthorizationToken(WebTarget webTarget, String id, String requestFile) throws IOException {
        return webTarget
                .path("/payments/" + id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(RequestDataHelper.getFileFromResources(requestFile)));
    }

    public static Response executeDeletePaymentNoAuthorizationToken(WebTarget webTarget, String id) {
        return webTarget
                .path("/payments/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    // requests to server
    public static Response executeGetPayments(WebTarget webTarget) {
        return webTarget
                .path("/payments")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "some-authorization-code")
                .get();
    }

    public static Response executeGetFullDataPayments(WebTarget webTarget) {
        return webTarget
                .path("/payments/full")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "some-authorization-code")
                .get();
    }

    public static Response executeGetPaymentById(WebTarget webTarget, String id) {
        return webTarget
                .path("/payments/" + id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "some-authorization-code")
                .get();
    }

    public static Response executeCreatePayment(WebTarget webTarget, String requestFile) throws IOException {
        return webTarget
                .path("/payments")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "some-authorization-code")
                .post(Entity.json(RequestDataHelper.getFileFromResources(requestFile)));
    }

    public static Response executeUpdatePayment(WebTarget webTarget, String id, String requestFile) throws IOException {
        return webTarget
                .path("/payments/" + id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "some-authorization-code")
                .put(Entity.json(RequestDataHelper.getFileFromResources(requestFile)));
    }

    public static Response executeDeletePayment(WebTarget webTarget, String id, String version) {
        return webTarget
                .path("/payments/" + id + "/" + version)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "some-authorization-code")
                .delete();
    }
}
