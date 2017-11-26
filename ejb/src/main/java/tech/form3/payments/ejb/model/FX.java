package tech.form3.payments.ejb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Victor Oniga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FX implements Serializable {
    public static final long serialVersionUID = 1L;

    @Column(name = "CONTRACT_REFERENCE")
    @JsonProperty(value = "contract_reference")
    private String contractReference;

    @Column(name = "EXCHANGE_RATE")
    @JsonProperty(value = "exchange_rate")
    private String exchangeRate;

    @Column(name = "ORIGINAL_AMOUNT")
    @JsonProperty(value = "original_amount")
    private String originalAmount;

    @Column(name = "ORIGINAL_CURRENCY_ID")
    @JsonProperty(value = "original_currency")
    private String originalCurrency;

    public String getContractReference() {
        return contractReference;
    }

    public void setContractReference(String contractReference) {
        this.contractReference = contractReference;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }
}
