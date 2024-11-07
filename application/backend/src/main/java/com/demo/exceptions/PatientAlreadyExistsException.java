package com.demo.exceptions;

public class PatientAlreadyExistsException extends Exception {
    public PatientAlreadyExistsException() {
    }

    public PatientAlreadyExistsException(String message) {
        super(message);
    }

    public PatientAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PatientAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public PatientAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
