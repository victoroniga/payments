package tech.form3.payments.ejb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.form3.payments.ejb.model.ErrorCode;
import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.model.PaymentException;
import tech.form3.payments.ejb.persistence.PaymentPersistence;
import tech.form3.payments.ejb.service.PaymentService;
import tech.form3.payments.ejb.util.ExceptionsUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Victor Oniga
 */
@Stateless
public class PaymentServiceImpl implements PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @EJB
    private PaymentPersistence paymentPersistence;

    @PersistenceContext
    private EntityManager em;

    public List<Payment> getAllPayments() throws PaymentException {
        logger.debug("getAllPayments - Returning all payments");
        try {
            return paymentPersistence.getAllPayments();
        } catch (Exception ex) {
            throw new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "Unexpected database error encountered", ex);
        }
    }

    public Payment getPaymentById(String id) throws PaymentException {
        logger.debug("getPaymentById - Returning payment by id: " + id);
        try {
            Payment payment = paymentPersistence.getPaymentById(id);
            if (payment == null) {
                throw new PaymentException(ErrorCode.ENTITY_NOT_FOUND, "Payment with id " + id + " does NOT exist");
            }

            return payment;
        } catch (PaymentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "Unexpected database error encountered", ex);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Payment createPayment(Payment payment) throws PaymentException {
        logger.debug("createPayment - Creating payment: " + payment.getId());
        try {
            Payment updatedPayment = paymentPersistence.createPayment(payment);
            em.flush();

            return updatedPayment;
        } catch (PaymentException ex) {
            throw ex;
        } catch (Exception ex) {
            if (ExceptionsUtil.exceptionChainContains(ex, "ConstraintViolationException")) {
                throw new PaymentException(ErrorCode.CONSTRAINTS, "Constraints check failed", ex);
            }

            throw new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "Got an exception on createPayment", ex);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Payment updatePayment(Payment payment) throws PaymentException {
        logger.debug("updatePayment - Updating payment: " + payment.getId());
        try {
            Payment updatedPayment = paymentPersistence.updatePayment(payment);
            em.flush();

            return updatedPayment;
        } catch (PaymentException ex) {
            throw ex;
        } catch (Exception ex) {
            if (ExceptionsUtil.exceptionChainContains(ex, "ConstraintViolationException")) {
                throw new PaymentException(ErrorCode.CONSTRAINTS, "Constraints check failed", ex);
            } else if (ExceptionsUtil.exceptionChainContains(ex, "OptimisticLockException")) {
                throw new PaymentException(ErrorCode.OBJECT_ALREADY_MODIFIED, "Payment has already been modified in another operation. Please refresh your page and try again", ex);
            }

            throw new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "Got an exception on updatePayment", ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePayment(Payment payment) throws PaymentException {
        logger.debug("deletePayment - Deleting payment: " + payment.getId());
        try {
            paymentPersistence.deletePayment(payment);
            em.flush();
        } catch (PaymentException ex) {
            throw ex;
        } catch (Exception ex) {
            if (ExceptionsUtil.exceptionChainContains(ex, "OptimisticLockException")) {
                throw new PaymentException(ErrorCode.OBJECT_ALREADY_MODIFIED, "Payment has already been modified in another operation. Please refresh your page and try again", ex);
            }

            throw new PaymentException(ErrorCode.UNEXPECTED_DATABASE_ERROR, "Unexpected database error encountered", ex);
        }
    }
}
