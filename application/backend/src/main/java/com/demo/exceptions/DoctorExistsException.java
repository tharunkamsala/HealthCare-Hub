package com.demo.exceptions;

public class DoctorExistsException extends Exception{
    public DoctorExistsException() {
    }

    public DoctorExistsException(String message) {
        super(message);
    }

    public DoctorExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoctorExistsException(Throwable cause) {
        super(cause);
    }

    public DoctorExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
