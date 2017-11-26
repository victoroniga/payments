package tech.form3.payments.ejb.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Victor Oniga
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentsJsonFormat {
    String[] value();
}
