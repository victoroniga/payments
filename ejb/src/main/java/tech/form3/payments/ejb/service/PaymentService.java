package tech.form3.payments.ejb.service;

import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.model.PaymentException;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Victor Oniga
 */
@Remote
public interface PaymentService {
    List<Payment> getAllPayments() throws PaymentException;

    Payment getPaymentById(String id) throws PaymentException;

    Payment createPayment(Payment payment) throws PaymentException;

    Payment updatePayment(Payment payment) throws PaymentException;

    void deletePayment(Payment payment) throws PaymentException;
}
