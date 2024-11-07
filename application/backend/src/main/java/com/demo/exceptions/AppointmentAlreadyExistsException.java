package com.demo.exceptions;

public class AppointmentAlreadyExistsException extends Exception{
    public AppointmentAlreadyExistsException() {
    }

    public AppointmentAlreadyExistsException(String message) {
        super(message);
    }

    public AppointmentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointmentAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public AppointmentAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
