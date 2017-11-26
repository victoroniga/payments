package tech.form3.payments.it;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.form3.payments.ejb.model.Payment;
import tech.form3.payments.ejb.model.PaymentException;
import tech.form3.payments.ejb.persistence.PaymentPersistence;

import javax.ejb.EJB;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Victor Oniga
 */
@RunWith(Arquillian.class)
public class PaymentPersistenceIT {
    @EJB
    private PaymentPersistence paymentPersistence;

    @Deployment
    public static Archive<?> createDeploymentPackage() {
        final Archive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, PaymentPersistence.class.getPackage(), Payment.class.getPackage())
                .addAsDirectory("META-INF")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("beans.xml", "beans.xml")
                .addAsWebInfResource("wildfly-ds.xml", "wildfly-ds.xml");

        return archive;
    }

    @Test
    @InSequence
    public void testGetNoPayments() throws PaymentException, InterruptedException {
        List<Payment> payments = paymentPersistence.getAllPayments();
        assertNotNull(payments);
        assertEquals(0, payments.size());
    }

    @Test
    @InSequence(1)
    public void testGetPaymentByIdReturnNull() throws PaymentException {
        Payment payment = paymentPersistence.getPaymentById(null);
        assertNull(payment);
    }

    @Test
    @InSequence(2)
    public void testCreatePayment() throws PaymentException {
        Payment payment = new Payment();
        payment.setOrganizationId("organization-id");

        Payment updatedPayment = paymentPersistence.createPayment(payment);
        assertEquals(payment.getOrganizationId(), updatedPayment.getOrganizationId());

    }

    @Test
    @InSequence(3)
    public void testUpdatePayment() throws PaymentException {
        List<Payment> payments = paymentPersistence.getAllPayments();
        assertEquals(1, payments.size());
        Payment payment = payments.get(0);
        payment.setOrganizationId("organization-id2");

        Payment updatedPayment = paymentPersistence.updatePayment(payment);
        assertEquals(payment.getOrganizationId(), updatedPayment.getOrganizationId());

        for (Payment paymentToDelete : paymentPersistence.getAllPayments()) {
            paymentPersistence.deletePayment(paymentToDelete);
        }

        payments = paymentPersistence.getAllPayments();
        assertEquals(0, payments.size());
    }
}
