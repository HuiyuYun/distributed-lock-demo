package org.yunhuiyu.redlock.exception;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 16:54
 * Description:
 */
public class UnableToAquireLockException extends RuntimeException {

    public UnableToAquireLockException() {
        super();
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
