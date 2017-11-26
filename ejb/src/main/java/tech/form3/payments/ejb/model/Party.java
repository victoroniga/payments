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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class Party implements Serializable {
    public static final long serialVersionUID = 1L;

    @Column(name = "ACCOUNT_NAME")
    @JsonProperty(value = "account_name")
    private String accountName;

    @Column(name = "ACCOUNT_NUMBER")
    @JsonProperty(value = "account_number")
    private String accountNumber;

    @Column(name = "ACCOUNT_NUMBER_CODE")
    @JsonProperty(value = "account_number_code")
    private String accountNumberCode;

    @Column(name = "ACCOUNT_TYPE")
    @JsonProperty(value = "account_type")
    private Integer accountType;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "BANK_ID")
    @JsonProperty(value = "bank_id")
    private String bankId;

    @Column(name = "BANK_ID_CODE")
    @JsonProperty(value = "bank_id_code")
    private String bankIdCode;

    @Column(name = "NAME")
    private String name;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumberCode() {
        return accountNumberCode;
    }

    public void setAccountNumberCode(String accountNumberCode) {
        this.accountNumberCode = accountNumberCode;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankIdCode() {
        return bankIdCode;
    }

    public void setBankIdCode(String bankIdCode) {
        this.bankIdCode = bankIdCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
