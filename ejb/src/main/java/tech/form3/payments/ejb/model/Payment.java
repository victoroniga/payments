package tech.form3.payments.ejb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Victor Oniga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "PAYMENT")
public class Payment implements Serializable {
    public static final long serialVersionUID = 1L;

    @Column(name = "TYPE")
    private String type;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "ORGANISATION_ID")
    @JsonProperty(value = "organisation_id")
    private String organizationId;

    @Embedded
    @JsonProperty(value = "attributes")
    private PaymentAttributes attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public PaymentAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PaymentAttributes attributes) {
        this.attributes = attributes;
    }
}
