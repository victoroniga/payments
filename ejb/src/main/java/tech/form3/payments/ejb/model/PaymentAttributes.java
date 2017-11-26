package tech.form3.payments.ejb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import tech.form3.payments.ejb.util.PaymentDateDeserializer;
import tech.form3.payments.ejb.util.PaymentDateSerializer;
import tech.form3.payments.ejb.util.PaymentsJsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Victor Oniga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class PaymentAttributes implements Serializable {
    public static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "AMOUNT")
    private String amount;

    @Embedded
    @JsonProperty(value = "beneficiary_party")
    private Party beneficiary;

    @Embedded
    @JsonProperty(value = "charges_information")
    private ChargesInformation chargesInformation;

    @Column(name = "CURRENCY")
    private String currency;

    @Embedded
    @JsonProperty(value = "debtor_party")
    @AttributeOverrides({
            @AttributeOverride(
                    name = "accountName",
                    column = @Column(name = "DEBTOR_ACCOUNT_NAME")
            ),
            @AttributeOverride(
                    name = "accountNumber",
                    column = @Column(name = "DEBTOR_ACCOUNT_NUMBER")
            ),
            @AttributeOverride(
                    name = "accountNumberCode",
                    column = @Column(name = "DEBTOR_ACCOUNT_NUMBER_CODE")
            ),
            @AttributeOverride(
                    name = "accountType",
                    column = @Column(name = "DEBTOR_ACCOUNT_TYPE")
            ),
            @AttributeOverride(
                    name = "address",
                    column = @Column(name = "DEBTOR_ADDRESS")
            ),
            @AttributeOverride(
                    name = "bankId",
                    column = @Column(name = "DEBTOR_BANK_ID")
            ),
            @AttributeOverride(
                    name = "bankIdCode",
                    column = @Column(name = "DEBTOR_BANK_ID_CODE")
            ),
            @AttributeOverride(
                    name = "name",
                    column = @Column(name = "DEBTOR_NAME")
            )
    })
    private Party debtor;

    @Column(name = "END_TO_END_REFERENCE")
    @JsonProperty(value = "end_to_end_reference")
    private String endToEndReference;

    @Embedded
    private FX fx;

    @Column(name = "NUMERIC_REFERENCE")
    @JsonProperty(value = "numeric_reference")
    private String numericReference;

    @Column(name = "PAYMENT_ID")
    @JsonProperty(value = "payment_id")
    private String paymentId;

    @Column(name = "PAYMENT_PURPOSE")
    @JsonProperty(value = "payment_purpose")
    private String paymentPurpose;

    @Column(name = "PAYMENT_SCHEME")
    @JsonProperty(value = "payment_scheme")
    private String paymentScheme;

    @Column(name = "PAYMENT_TYPE")
    @JsonProperty(value = "payment_type")
    private String paymentType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROCESSING_DATE")
    @PaymentsJsonFormat("yyyy-MM-dd")
    @JsonSerialize(using = PaymentDateSerializer.class)
    @JsonDeserialize(using = PaymentDateDeserializer.class)
    @JsonProperty(value = "processing_date")
    private Date processingDate;

    @NotNull
    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "SCHEME_PAYMENT_SUB_TYPE_ID")
    @JsonProperty(value = "scheme_payment_sub_type")
    private String schemePaymentSubType;

    @Column(name = "SCHEME_PAYMENT_TYPE_ID")
    @JsonProperty(value = "scheme_payment_type")
    private String schemePaymentType;

    @Embedded
    @JsonProperty(value = "sponsor_party")
    @AttributeOverrides({
            @AttributeOverride(
                    name = "accountName",
                    column = @Column(name = "SPONSOR_ACCOUNT_NAME")
            ),
            @AttributeOverride(
                    name = "accountNumber",
                    column = @Column(name = "SPONSOR_ACCOUNT_NUMBER")
            ),
            @AttributeOverride(
                    name = "accountNumberCode",
                    column = @Column(name = "SPONSOR_ACCOUNT_NUMBER_CODE")
            ),
            @AttributeOverride(
                    name = "accountType",
                    column = @Column(name = "SPONSOR_ACCOUNT_TYPE")
            ),
            @AttributeOverride(
                    name = "address",
                    column = @Column(name = "SPONSOR_ADDRESS")
            ),
            @AttributeOverride(
                    name = "bankId",
                    column = @Column(name = "SPONSOR_BANK_ID")
            ),
            @AttributeOverride(
                    name = "bankIdCode",
                    column = @Column(name = "SPONSOR_BANK_ID_CODE")
            ),
            @AttributeOverride(
                    name = "name",
                    column = @Column(name = "SPONSOR_NAME")
            )
    })
    private Party sponsor;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Party getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Party beneficiary) {
        this.beneficiary = beneficiary;
    }

    public ChargesInformation getChargesInformation() {
        return chargesInformation;
    }

    public void setChargesInformation(ChargesInformation chargesInformation) {
        this.chargesInformation = chargesInformation;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Party getDebtor() {
        return debtor;
    }

    public void setDebtor(Party debtor) {
        this.debtor = debtor;
    }

    public String getEndToEndReference() {
        return endToEndReference;
    }

    public void setEndToEndReference(String endToEndReference) {
        this.endToEndReference = endToEndReference;
    }

    public FX getFx() {
        return fx;
    }

    public void setFx(FX fx) {
        this.fx = fx;
    }

    public String getNumericReference() {
        return numericReference;
    }

    public void setNumericReference(String numericReference) {
        this.numericReference = numericReference;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getPaymentScheme() {
        return paymentScheme;
    }

    public void setPaymentScheme(String paymentScheme) {
        this.paymentScheme = paymentScheme;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSchemePaymentSubType() {
        return schemePaymentSubType;
    }

    public void setSchemePaymentSubType(String schemePaymentSubType) {
        this.schemePaymentSubType = schemePaymentSubType;
    }

    public String getSchemePaymentType() {
        return schemePaymentType;
    }

    public void setSchemePaymentType(String schemePaymentType) {
        this.schemePaymentType = schemePaymentType;
    }

    public Party getSponsor() {
        return sponsor;
    }

    public void setSponsor(Party sponsor) {
        this.sponsor = sponsor;
    }
}
