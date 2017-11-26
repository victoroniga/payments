package tech.form3.payments.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tech.form3.payments.ejb.model.ErrorCode;

/**
 * Created by Victor Oniga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusDTO {
    private OperationType operation;
    private String reason;
    private ErrorCode errorCode;

    public StatusDTO(OperationType operation) {
        this.operation = operation;
    }

    public StatusDTO(OperationType operation, String reason) {
        this.operation = operation;
        this.reason = reason;
    }

    public StatusDTO(OperationType operation, ErrorCode errorCode, String reason) {
        this.operation = operation;
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public StatusDTO(ErrorCode errorCode) {
        this.errorCode = errorCode;
        operation = OperationType.ERROR;
    }

    public StatusDTO(ErrorCode errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
        this.operation = OperationType.ERROR;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
