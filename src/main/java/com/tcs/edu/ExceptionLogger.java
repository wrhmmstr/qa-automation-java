package com.tcs.edu;

public class ExceptionLogger extends RuntimeException {
    public ExceptionLogger() {
        super();
    }

    public ExceptionLogger(String message) {
        super(message);
    }

    public ExceptionLogger(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionLogger(Throwable cause) {
        super(cause);
    }

    protected ExceptionLogger(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
