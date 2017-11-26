package tech.form3.payments.ejb.persistence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.persistence.PaymentPersistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Victor Oniga
 */
@Stateless
public class PaymentPersistenceImpl implements PaymentPersistence {
    private static final Logger logger = LoggerFactory.getLogger(PaymentPersistenceImpl.class);

    @PersistenceContext
    private EntityManager em;

    public List<Payment> getAllPayments() {
        logger.debug("getAllPayments - Getting all payments");
        return em.createQuery("SELECT p FROM PAYMENT p", Payment.class).getResultList();
    }

    public Payment getPaymentById(String id) {
        logger.debug("getPaymentById - Getting payment by id: " + id);
        if (id == null) {
            return null;
        }

        Payment payment = em.find(Payment.class, id);
        logger.debug("Got payment by id " + payment);

        return payment;
    }

    public Payment createPayment(Payment payment) {
        logger.debug("createPayment - Creating payment: " + payment.getId());
        Payment newPayment = em.merge(payment);
        logger.debug("Successfully created payment " + newPayment.getId());

        return newPayment;
    }

    public Payment updatePayment(Payment payment) {
        logger.debug("updatePayment - Updating payment: " + payment.getId());
        Payment updatedPayment = em.merge(payment);
        logger.debug("Successfully updated payment " + updatedPayment.getId());

        return updatedPayment;
    }

    public void deletePayment(Payment payment) {
        logger.debug("deletePayment - Deleting payment with id: " + payment.getId());
        if (!em.contains(payment)) {
            payment = em.merge(payment);
        }

        em.remove(payment);
        logger.debug("Successfully deleted payment with id: " + payment.getId());
    }
}
