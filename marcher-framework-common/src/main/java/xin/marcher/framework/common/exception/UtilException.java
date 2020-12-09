package xin.marcher.framework.common.exception;

/**
 * 工具类异常
 *
 * @author marcher
 */
public class UtilException extends RuntimeException {

    public UtilException(String message) {
        super(message);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
