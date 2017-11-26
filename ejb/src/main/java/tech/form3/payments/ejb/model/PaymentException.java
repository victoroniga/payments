package tech.form3.payments.ejb.model;

/**
 * Created by Victor Oniga
 */
public class PaymentException extends Exception {
    private ErrorCode errorCode;

    public PaymentException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public PaymentException(ErrorCode errorCode, String errorMessage, Exception cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return super.getMessage();
    }
}
