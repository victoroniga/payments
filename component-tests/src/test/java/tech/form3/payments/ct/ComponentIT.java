package tech.form3.payments.ct;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.form3.payments.web.security.SecurityFilter;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static tech.form3.payments.ct.RequestDataHelper.*;

/**
 * Created by Victor Oniga
 */
@RunWith(Arquillian.class)
public class ComponentIT {
    @Deployment(testable = false)
    public static Archive<?> createDeployment() {
        File[] webDependencies = Maven.resolver().loadPomFromFile("../web/pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        Archive archive = ShrinkWrap.create(WebArchive.class, "web.war")
                .addPackages(true, "tech.form3.payments")
                .addPackages(true, "tech.form3.payments.web")
                .addClass(SecurityFilter.class)
                .addAsLibraries(webDependencies)
                .addAsDirectory("META-INF")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("beans.xml", "beans.xml")
                .addAsWebInfResource("wildfly-ds.xml", "wildfly-ds.xml")
                .addAsWebInfResource("web.xml");

        archive.as(ZipExporter.class).exportTo(new File("target/" + archive.getName()), true);

        return archive;
    }

    @Test
    @RunAsClient
    @InSequence
    public void testNoAuthorizationToken(@ArquillianResteasyResource("v1") final WebTarget webTarget) throws IOException {
        Response response = RequestHelper.executeGetFullDataPaymentsNoAuthorizationToken(webTarget);
        assertEquals(401, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_ERROR_401);

        response = RequestHelper.executeGetPaymentsNoAuthorizationToken(webTarget);
        assertEquals(401, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_ERROR_401);

        response = RequestHelper.executeGetPaymentByIdNoAuthorizationToken(webTarget, "some-id");
        assertEquals(401, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_ERROR_401);

        response = RequestHelper.executeCreatePaymentNoAuthorizationToken(webTarget, REQUEST_CREATE_PAYMENT1);
        assertEquals(401, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_ERROR_401);

        response = RequestHelper.executeUpdatePaymentNoAuthorizationToken(webTarget, "some-id", REQUEST_CREATE_PAYMENT1);
        assertEquals(401, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_ERROR_401);

        response = RequestHelper.executeDeletePaymentNoAuthorizationToken(webTarget, "some-id");
        assertEquals(401, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_ERROR_401);
    }

    @Test
    @RunAsClient
    @InSequence(1)
    public void testGetNoPayments(@ArquillianResteasyResource("v1") final WebTarget webTarget) throws IOException {
        Response response = RequestHelper.executeGetFullDataPayments(webTarget);
        assertEquals(200, response.getStatus());
        assertArrays(response, EXPECTED_RESPONSE_NO_PAYMENTS);

        response = RequestHelper.executeGetPayments(webTarget);
        assertEquals(200, response.getStatus());
        assertArrays(response, EXPECTED_RESPONSE_NO_PAYMENTS);

        response = RequestHelper.executeGetPaymentById(webTarget, "non-existing-id");
        assertEquals(500, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_GET_BY_ID_ERROR, "reason");
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void testCreatePayment(@ArquillianResteasyResource("v1") final WebTarget webTarget) throws IOException {
        Response response = RequestHelper.executeCreatePayment(webTarget, REQUEST_CREATE_PAYMENT1);
        assertEquals(200, response.getStatus());
        JSONObject payment = assertObjects(response, EXPECTED_RESPONSE_CREATE_PAYMENT1, "id", "version");

        response = RequestHelper.executeGetPaymentById(webTarget, payment.getString("id"));
        assertEquals(200, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_CREATE_PAYMENT1, "id");

        // clean up, delete the payment
        response = RequestHelper.executeDeletePayment(webTarget, payment.getString("id"), String.valueOf(payment.getLong("version")));
        assertEquals(200, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_SUCCESS);

        // get payments count - should be 0
        checkPaymentsCount(webTarget, 0);
    }

    @Test
    @RunAsClient
    @InSequence(3)
    public void testUpdatePayments(@ArquillianResteasyResource("v1") final WebTarget webTarget) throws IOException {
        // create and update payment1
        Response response = RequestHelper.executeCreatePayment(webTarget, REQUEST_CREATE_PAYMENT1);
        assertEquals(200, response.getStatus());
        JSONObject payment = assertObjects(response, EXPECTED_RESPONSE_CREATE_PAYMENT1, "id", "version");

        // get payments count - should be 1
        checkPaymentsCount(webTarget, 1);

        response = RequestHelper.executeUpdatePayment(webTarget, payment.getString("id"), REQUEST_UPDATE_PAYMENT1);
        assertEquals(200, response.getStatus());
        payment = assertObjects(response, EXPECTED_RESPONSE_UPDATE_PAYMENT1, "id");

        // get payments count - should be 1
        checkPaymentsCount(webTarget, 1);

        response = RequestHelper.executeGetPaymentById(webTarget, payment.getString("id"));
        assertEquals(200, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_UPDATE_PAYMENT1, "id");

        // create and update payment2
        response = RequestHelper.executeCreatePayment(webTarget, REQUEST_CREATE_PAYMENT2);
        assertEquals(200, response.getStatus());
        payment = assertObjects(response, EXPECTED_RESPONSE_CREATE_PAYMENT2, "id", "version");

        // get payments count - should be 2
        checkPaymentsCount(webTarget, 2);

        response = RequestHelper.executeUpdatePayment(webTarget, payment.getString("id"), REQUEST_UPDATE_PAYMENT2);
        assertEquals(200, response.getStatus());
        assertObjects(response, EXPECTED_RESPONSE_UPDATE_PAYMENT2, "id");

        // get payments count - should be 2
        JSONArray payments = checkPaymentsCount(webTarget, 2);

        // clean up, delete the payments
        for (Object o : payments) {
            payment = (JSONObject) o;

            response = RequestHelper.executeDeletePayment(webTarget, payment.getString("id"), String.valueOf(payment.getLong("version")));
            assertEquals(200, response.getStatus());
            assertObjects(response, EXPECTED_RESPONSE_SUCCESS);
        }

        // get payments count - should be 0
        checkPaymentsCount(webTarget, 0);
    }

    private JSONArray checkPaymentsCount(WebTarget webTarget, int count) throws IOException {
        Response response = RequestHelper.executeGetFullDataPayments(webTarget);
        assertEquals(200, response.getStatus());
        return assertArraysSize(response, count);
    }

    private JSONObject assertObjects(Response response, String expectedResponseFile, String... ignoredFields) throws IOException {
        String responseBody = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject expectedResponse = new JSONObject(RequestDataHelper.getFileFromResources(expectedResponseFile));

        // reset ignoredFields
        for (String ignoredField : ignoredFields) {
            expectedResponse.put(ignoredField, jsonObject.get(ignoredField));
        }

        JsonNode expectedTree = mapper.readTree(expectedResponse.toString());
        JsonNode responseTree = mapper.readTree(jsonObject.toString());
        assertEquals(expectedTree, responseTree);

        return jsonObject;
    }

    private JSONArray assertArrays(Response response, String expectedResponseFile) throws IOException {
        String responseBody = response.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONArray expectedResponse = new JSONArray(RequestDataHelper.getFileFromResources(expectedResponseFile));

        JsonNode expectedTree = mapper.readTree(expectedResponse.toString());
        JsonNode responseTree = mapper.readTree(jsonArray.toString());
        assertEquals(expectedTree, responseTree);

        return jsonArray;
    }

    private JSONArray assertArraysSize(Response response, int expectedCount) throws IOException {
        String responseBody = response.readEntity(String.class);
        JSONArray jsonArray = new JSONArray(responseBody);
        assertEquals(expectedCount, jsonArray.length());

        return jsonArray;
    }
}
