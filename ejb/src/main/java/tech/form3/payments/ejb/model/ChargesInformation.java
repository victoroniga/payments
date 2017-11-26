package tech.form3.payments.ejb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor Oniga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class ChargesInformation implements Serializable {
    public static final long serialVersionUID = 1L;

    @Column(name = "BEARER_CODE")
    @JsonProperty(value = "bearer_code")
    private String bearerCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(value = "sender_charges")
    private List<Charge> senderCharges = new ArrayList<>();

    @Column(name = "RECEIVER_CHARGES_AMOUNT")
    @JsonProperty(value = "receiver_charges_amount")
    private String receiverChargesAmount;

    @Column(name = "RECEIVER_CHARGES_CURRENCY")
    @JsonProperty(value = "receiver_charges_currency")
    private String receiverChargesCurrency;

    public String getBearerCode() {
        return bearerCode;
    }

    public void setBearerCode(String bearerCode) {
        this.bearerCode = bearerCode;
    }

    public List<Charge> getSenderCharges() {
        return senderCharges;
    }

    public void setSenderCharges(List<Charge> senderCharges) {
        this.senderCharges = senderCharges;
    }

    public String getReceiverChargesAmount() {
        return receiverChargesAmount;
    }

    public void setReceiverChargesAmount(String receiverChargesAmount) {
        this.receiverChargesAmount = receiverChargesAmount;
    }

    public String getReceiverChargesCurrency() {
        return receiverChargesCurrency;
    }

    public void setReceiverChargesCurrency(String receiverChargesCurrency) {
        this.receiverChargesCurrency = receiverChargesCurrency;
    }
}
