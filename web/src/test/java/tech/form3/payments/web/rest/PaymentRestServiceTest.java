package tech.form3.payments.web.rest;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tech.form3.payments.ejb.model.ErrorCode;
import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.model.PaymentException;
import tech.form3.payments.ejb.service.impl.PaymentServiceImpl;
import tech.form3.payments.web.dto.OperationType;
import tech.form3.payments.web.dto.StatusDTO;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Victor Oniga
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentRestServiceTest {
    @InjectMocks
    private PaymentRestService paymentRestService;

    @Mock
    private PaymentServiceImpl paymentService;

    @Test
    @SuppressWarnings("unchecked")
    public void testGetFullDataPaymentsException() throws PaymentException {
        // mock calls to service layer
        PaymentException PaymentException = new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "");
        doThrow(PaymentException).when(paymentService).getAllPayments();

        Response response = paymentRestService.getFullDataPayments();
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof StatusDTO);
        StatusDTO statusDTO = (StatusDTO) response.getEntity();
        assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, statusDTO.getErrorCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetFullDataPayments() throws PaymentException {
        // mock calls to service layer
        List<Payment> mockedPayments = mock(ArrayList.class);
        when(mockedPayments.size()).thenReturn(2);
        when(paymentService.getAllPayments()).thenReturn(mockedPayments);

        Response response = paymentRestService.getFullDataPayments();
        List<Payment> payments = (List<Payment>) response.getEntity();
        assertNotNull(payments);
        assertEquals(2, payments.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllPaymentsException() throws PaymentException {
        // mock calls to service layer
        PaymentException PaymentException = new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "");
        doThrow(PaymentException).when(paymentService).getAllPayments();

        Response response = paymentRestService.getAllPayments();
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof StatusDTO);
        StatusDTO statusDTO = (StatusDTO) response.getEntity();
        assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, statusDTO.getErrorCode());
    }

    @Ignore
    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllPayments() throws PaymentException {
        // mock calls to service layer
        List<Payment> mockedPayments = Collections.singletonList(getPayment());
        when(paymentService.getAllPayments()).thenReturn(mockedPayments);

        Response response = paymentRestService.getAllPayments();
        List<Payment> payments = (List<Payment>) response.getEntity();
        assertNotNull(payments);
        assertEquals(mockedPayments.size(), payments.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetPaymentByIdException() throws PaymentException {
        // mock calls to service layer
        PaymentException paymentException = new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "");
        doThrow(paymentException).when(paymentService).getPaymentById(anyString());

        Response response = paymentRestService.getPaymentById("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof StatusDTO);
        StatusDTO statusDTO = (StatusDTO) response.getEntity();
        assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, statusDTO.getErrorCode());
    }

    @Ignore
    @Test
    @SuppressWarnings("unchecked")
    public void testGetPaymentById() throws PaymentException {
        // mock calls to service layer
        Payment mockedPayment = mock(Payment.class);
        when(mockedPayment.getId()).thenReturn("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        when(paymentService.getPaymentById(anyString())).thenReturn(mockedPayment);

        Response response = paymentRestService.getPaymentById("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        Payment payment = (Payment) response.getEntity();
        assertNotNull(payment);
        assertEquals("09a8fe0d-e239-4aff-8098-7923eadd0b98", payment.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatePaymentException() throws PaymentException {
        // mock calls to service layer
        Payment mockedPayment = getPayment();
        PaymentException PaymentException = new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "");
        doThrow(PaymentException).when(paymentService).createPayment(any(Payment.class));

        Response response = paymentRestService.createPayment(mockedPayment);
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof StatusDTO);
        StatusDTO statusDTO = (StatusDTO) response.getEntity();
        assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, statusDTO.getErrorCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatePayment() throws PaymentException {
        // mock calls to service layer
        Payment mockedPayment = getPayment();
        when(paymentService.createPayment(mockedPayment)).thenReturn(mockedPayment);

        Response response = paymentRestService.createPayment(mockedPayment);
        Payment payment = (Payment) response.getEntity();
        assertNotNull(payment);
        assertEquals(mockedPayment, payment);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePaymentException() throws PaymentException {
        // mock calls to service layer
        Payment mockedPayment = getPayment();
        PaymentException PaymentException = new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "");
        doThrow(PaymentException).when(paymentService).updatePayment(any(Payment.class));

        Response response = paymentRestService.updatePayment("09a8fe0d-e239-4aff-8098-7923eadd0b98", mockedPayment);
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof StatusDTO);
        StatusDTO statusDTO = (StatusDTO) response.getEntity();
        assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, statusDTO.getErrorCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePayment() throws PaymentException {
        // mock calls to service layer
        Payment mockedPayment = getPayment();
        when(paymentService.updatePayment(mockedPayment)).thenReturn(mockedPayment);

        Response response = paymentRestService.updatePayment("09a8fe0d-e239-4aff-8098-7923eadd0b98", mockedPayment);
        Payment payment = (Payment) response.getEntity();
        assertNotNull(payment);
        assertEquals(mockedPayment, payment);
    }

    @Test
    public void testDeletePaymentError() throws PaymentException {
        // mock calls to service layer
        Payment mockedPayment = getPayment();
        when(paymentService.getPaymentById(anyString())).thenReturn(mockedPayment);
        doThrow(PaymentException.class).when(paymentService).deletePayment(mockedPayment);

        Response response = paymentRestService.deletePayment("09a8fe0d-e239-4aff-8098-123456789012", 1L);
        StatusDTO status = (StatusDTO) response.getEntity();
        assertNotNull(status);
        assertEquals(OperationType.ERROR, status.getOperation());
    }

    @Test
    public void testDeletePaymentSuccess() throws PaymentException {
        // mock calls to service layer
        Payment mockedPayment = getPayment();
        when(paymentService.getPaymentById(anyString())).thenReturn(mockedPayment);

        Response response = paymentRestService.deletePayment("09a8fe0d-e239-4aff-8098-7923eadd0b98", 1L);
        StatusDTO status = (StatusDTO) response.getEntity();
        assertNotNull(status);
        assertEquals(OperationType.SUCCESS, status.getOperation());
    }

    private Payment getPayment() {
        Payment payment = new Payment();
        payment.setId("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        payment.setVersion(1L);

        return payment;
    }
}
