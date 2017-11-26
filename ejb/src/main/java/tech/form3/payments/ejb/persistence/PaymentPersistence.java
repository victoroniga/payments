package tech.form3.payments.ejb.persistence;

import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.model.PaymentException;

import java.util.List;

/**
 * Created by Victor Oniga
 */
public interface PaymentPersistence {
    List<Payment> getAllPayments();

    Payment getPaymentById(String id);

    Payment createPayment(Payment payment) throws PaymentException;

    Payment updatePayment(Payment payment) throws PaymentException;

    void deletePayment(Payment payment) throws PaymentException;
}
