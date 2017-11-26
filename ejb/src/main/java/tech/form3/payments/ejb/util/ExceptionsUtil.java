package tech.form3.payments.ejb.util;

/**
 * Created by Victor Oniga
 */
public class ExceptionsUtil {
    public static boolean exceptionChainContains(Throwable ex, String className) {
        while (ex.getCause() != null & !ex.equals(ex.getCause())) {
            ex = ex.getCause();
            if (ex.getClass().getCanonicalName().contains(className)) {
                return true;
            }
        }

        return false;
    }

    public static boolean exceptionChainDuplicates(Throwable ex) {
        return exceptionChainContainsMessage(ex, "Duplicate entry");
    }

    public static boolean exceptionChainContainsMessage(Throwable ex, String message) {
        while (ex.getCause() != null & !ex.equals(ex.getCause())) {
            ex = ex.getCause();
            if (ex.getMessage() != null && ex.getMessage().contains(message)) {
                return true;
            }
        }

        return false;
    }
}
