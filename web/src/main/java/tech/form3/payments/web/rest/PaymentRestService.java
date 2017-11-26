package tech.form3.payments.web.rest;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.model.PaymentException;
import tech.form3.payments.ejb.service.PaymentService;
import tech.form3.payments.web.dto.OperationType;
import tech.form3.payments.web.dto.StatusDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor Oniga
 */
@Path("/v1/payments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaymentRestService {
    private static Logger logger = LoggerFactory.getLogger(PaymentRestService.class);

    @EJB
    private PaymentService paymentService;

    @GET
    @Path("/full")
    public Response getFullDataPayments() {
        logger.debug("getFullDataPayments - Returning full data payments");
        Response.Status status = Response.Status.OK;
        Object result;

        try {
            result = paymentService.getAllPayments();
        } catch (PaymentException ex) {
            logger.error("Unexpected database error encountered", ex);
            result = new StatusDTO(ex.getErrorCode(), ex.getErrorMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(result)
                .build();
    }

    @GET
    public Response getAllPayments() {
        logger.debug("getAllPayments - Returning all payments");
        Response.Status status = Response.Status.OK;
        Object result;

        try {
            List<Payment> payments = paymentService.getAllPayments();
            List<Payment> shortVersionPayments = new ArrayList<>();

            Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
            for (Payment payment : payments) {
                Payment shortVersionPayment = mapper.map(payment, Payment.class, "short-version-payment");
                shortVersionPayments.add(shortVersionPayment);
            }

            result = shortVersionPayments;
        } catch (PaymentException ex) {
            logger.error("Unexpected database error encountered", ex);
            result = new StatusDTO(ex.getErrorCode(), ex.getErrorMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(result)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getPaymentById(@PathParam("id") String id) {
        logger.debug("getPaymentById - Returning payment with id: " + id);
        Response.Status status = Response.Status.OK;
        Object result;

        try {
            result = paymentService.getPaymentById(id);
        } catch (PaymentException ex) {
            logger.error("Unexpected database error encountered", ex);
            result = new StatusDTO(ex.getErrorCode(), ex.getErrorMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(result)
                .build();
    }

    @POST
    public Response createPayment(Payment payment) {
        logger.debug("createPayment - Creating new payment with id: " + payment.getId());
        Response.Status status = Response.Status.OK;
        Object result;

        try {
            result = paymentService.createPayment(payment);
        } catch (PaymentException ex) {
            logger.error("Unexpected database error encountered", ex);
            result = new StatusDTO(ex.getErrorCode(), ex.getErrorMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePayment(@PathParam("id") String id, Payment payment) {
        logger.debug("updatePayment - Updating payment: " + payment.getId());
        Response.Status status = Response.Status.OK;
        Object result;

        try {
            payment.setId(id);
            result = paymentService.updatePayment(payment);
        } catch (PaymentException ex) {
            logger.error("Error encountered", ex);
            result = new StatusDTO(ex.getErrorCode(), ex.getErrorMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(result)
                .build();
    }

    @DELETE
    @Path("/{id}/{version}")
    public Response deletePayment(@PathParam("id") String id, @PathParam("version") Long version) {
        logger.debug("deletePayment - Deleting payment with id: " + id);

        Response.Status status = Response.Status.OK;
        Object result = new StatusDTO(OperationType.SUCCESS);

        try {
            Payment payment = paymentService.getPaymentById(id);
            payment.setVersion(version);

            paymentService.deletePayment(payment);
            logger.debug("deletePayment - Successfully deleted payment with id: " + id);
        } catch (PaymentException ex) {
            logger.error("deletePayment - Could NOT delete payment with id: " + id + "; error code " + ex.getErrorCode(), ex);
            result = new StatusDTO(ex.getErrorCode(), ex.getErrorMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(result)
                .build();
    }
}
