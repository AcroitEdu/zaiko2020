/**
 *
 */
package jp.co.acroit.zaiko2020.auth;

/**
 * @author k-oishi
 *
 */
public class UserNotFoundException extends Exception {

    /**
     *
     */
    public UserNotFoundException() {
    }

    /**
     * @param message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
