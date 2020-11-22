package com.deadlyboundaries.common.model;

public class DeadlyBoundariesException extends RuntimeException {
    public DeadlyBoundariesException() {
    }

    public DeadlyBoundariesException(String message) {
        super(message);
    }

    public DeadlyBoundariesException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeadlyBoundariesException(Throwable cause) {
        super(cause);
    }

    public DeadlyBoundariesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
