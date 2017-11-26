package tech.form3.payments.ejb.persistence.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import tech.form3.payments.ejb.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Victor Oniga
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentPersistenceTest {
    @InjectMocks
    private PaymentPersistenceImpl paymentPersistence;

    @Mock
    private EntityManager em;

    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllPayments() {
        // mock calls to em
        List<Payment> mockedPayments = mock(ArrayList.class);
        when(mockedPayments.size()).thenReturn(2);
        TypedQuery query = mock(TypedQuery.class);
        when(em.createQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockedPayments);

        // make real call
        List<Payment> payments = paymentPersistence.getAllPayments();
        assertNotNull(payments);
        assertEquals(2, payments.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetPaymentById() {
        // mock calls to em
        Payment mockedPayment = getPayment();
        when(em.find(any(Class.class), any(Long.class))).thenReturn(mockedPayment);

        // make real call
        Payment payment = paymentPersistence.getPaymentById("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        assertNotNull(payment);
        assertEquals("09a8fe0d-e239-4aff-8098-7923eadd0b98", payment.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetPaymentByIdReturnNull() {
        // make real call
        Payment payment = paymentPersistence.getPaymentById(null);
        assertNull(payment);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatePayment() {
        // mock calls to em
        Payment mockedPayment = getPayment();
        mockedPayment.setId(null);
        when(em.merge(mockedPayment)).thenReturn(mockedPayment);

        // make real call
        Payment payment = paymentPersistence.createPayment(mockedPayment);
        assertNotNull(payment);
        assertEquals(mockedPayment, payment);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdatePayment() {
        // mock calls to em
        Payment mockedPayment = getPayment();
        when(em.find(any(Class.class), any(Long.class))).thenReturn(mockedPayment);
        when(em.merge(mockedPayment)).thenReturn(mockedPayment);

        // make real call
        Payment payment = paymentPersistence.updatePayment(mockedPayment);
        assertNotNull(payment);
        assertEquals(mockedPayment, payment);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeletePaymentSuccess() {
        // mock calls to em
        Payment mockedPayment = getPayment();
        when(em.merge(mockedPayment)).thenReturn(mockedPayment);

        // make real call
        paymentPersistence.deletePayment(mockedPayment);
        Mockito.verify(em, times(1)).remove(mockedPayment);
    }

    private Payment getPayment() {
        Payment payment = new Payment();
        payment.setId("09a8fe0d-e239-4aff-8098-7923eadd0b98");
        payment.setVersion(1L);

        return payment;
    }
}
