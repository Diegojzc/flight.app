package com.tokioschool.flight.app.core.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message){
        super(message);
    }

    public InternalErrorException(String message,Throwable cause) {
        super(message,cause);
    }
}
