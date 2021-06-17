package com.creditcard.exception;

public class UnauthorizedException extends SecurityException {

    private static final long serialVersionUID = 5310600375558058829L;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(cause);
    }
}
