package tech.form3.payments.ejb.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tech.form3.payments.ejb.model.ErrorCode;
import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.model.PaymentException;
import tech.form3.payments.ejb.persistence.PaymentPersistence;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Victor Oniga
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentPersistence paymentPersistence;

    @Mock
    private EntityManager em;

    @Test(expected = PaymentException.class)
    @SuppressWarnings("unchecked")
    public void testGetAllPaymentsException() throws PaymentException {
        // mock calls
        when(paymentPersistence.getAllPayments()).thenThrow(new RuntimeException("Exception from persistence layer"));

        // make real call
        List<Payment> payments = paymentService.getAllPayments();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllPayments() throws PaymentException {
        // mock calls
        when(paymentPersistence.getAllPayments()).thenReturn(Collections.singletonList(getPayment()));

        // make real call
        List<Payment> payments = paymentService.getAllPayments();
        assertNotNull(payments);
        assertEquals(1, payments.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetPaymentById() throws PaymentException {
        // mock calls
        when(paymentPersistence.getPaymentById(anyString())).thenReturn(getPayment());

        // make real call
        Payment payment = paymentService.getPaymentById("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        assertNotNull(payment);
        assertEquals("09a8fe0d-e239-4aff-8098-7923eadd0b98", payment.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetNullPaymentById() {
        // make real call
        try {
            paymentService.getPaymentById(null);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.ENTITY_NOT_FOUND, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetPaymentByIdException() {
        // mock calls
        when(paymentPersistence.getPaymentById(anyString())).thenThrow(new RuntimeException("Some unexpected error"));

        // make real call
        try {
            paymentService.getPaymentById("09a8fe0d-e239-4aff-8098-7923eadd0b98");
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatePaymentPaymentException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.createPayment(mockedPayment)).thenThrow(new PaymentException(ErrorCode.CONSTRAINTS, "Exception related to constraints"));

        // make real call
        try {
            paymentService.createPayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.CONSTRAINTS, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatePaymentConstraintViolationException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.createPayment(mockedPayment)).thenThrow(new RuntimeException(new ConstraintViolationException("Some exception from persistence layer", null, null)));

        // make real call
        try {
            paymentService.createPayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.CONSTRAINTS, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatePaymentException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.createPayment(mockedPayment)).thenThrow(new RuntimeException("Some exception from persistence layer"));

        // make real call
        try {
            paymentService.createPayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatePayment() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.createPayment(mockedPayment)).thenReturn(mockedPayment);

        // make real call
        Payment payment = paymentService.createPayment(mockedPayment);
        assertNotNull(payment);
        assertEquals(mockedPayment.getId(), payment.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePaymentPaymentException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.updatePayment(mockedPayment)).thenThrow(new PaymentException(ErrorCode.CONSTRAINTS, "Exception related to constraints"));

        // make real call
        try {
            paymentService.updatePayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.CONSTRAINTS, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePaymentConstraintViolationException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.updatePayment(mockedPayment)).thenThrow(new RuntimeException(new ConstraintViolationException("Some exception from persistence layer", null, null)));

        // make real call
        try {
            paymentService.updatePayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.CONSTRAINTS, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePaymentOptimisticLockException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.updatePayment(mockedPayment)).thenThrow(new RuntimeException(new OptimisticLockException("Some exception from persistence layer", null, null)));

        // make real call
        try {
            paymentService.updatePayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.OBJECT_ALREADY_MODIFIED, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePaymentException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.updatePayment(mockedPayment)).thenThrow(new RuntimeException("Some exception from persistence layer"));

        // make real call
        try {
            paymentService.updatePayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePayment() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        when(paymentPersistence.updatePayment(mockedPayment)).thenReturn(mockedPayment);

        // make real call
        Payment payment = paymentService.updatePayment(mockedPayment);
        assertNotNull(payment);
        assertEquals(mockedPayment.getId(), payment.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeletePaymentPaymentException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        doThrow(new PaymentException(ErrorCode.CONSTRAINTS, "Exception related to constraints")).when(paymentPersistence).deletePayment(mockedPayment);

        // make real call
        try {
            paymentService.deletePayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.CONSTRAINTS, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeletePaymentConstraintViolationException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        doThrow(new RuntimeException(new OptimisticLockException("Some exception from persistence layer", null, null))).when(paymentPersistence).deletePayment(mockedPayment);

        // make real call
        try {
            paymentService.deletePayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.OBJECT_ALREADY_MODIFIED, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeletePaymentException() throws PaymentException {
        // mock calls
        Payment mockedPayment = getPayment();
        doThrow(new RuntimeException("Some exception from persistence layer")).when(paymentPersistence).deletePayment(mockedPayment);

        // make real call
        try {
            paymentService.deletePayment(mockedPayment);
            fail("Test should fail");
        } catch (PaymentException ex) {
            assertEquals(ErrorCode.UNEXPECTED_DATABASE_ERROR, ex.getErrorCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeletePaymentSuccess() throws PaymentException {
        // make real call
        paymentService.deletePayment(getPayment());
    }

    private Payment getPayment() {
        Payment payment = new Payment();
        payment.setId("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        payment.setVersion(1L);

        return payment;
    }
}
