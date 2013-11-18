package com.freddrake.thecodebook;

/**
 *
 */
public class CodeBookException extends RuntimeException {
    public CodeBookException() {
        super();
    }

    public CodeBookException(String message) {
        super(message);
    }

    public CodeBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeBookException(Throwable cause) {
        super(cause);
    }

    protected CodeBookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
