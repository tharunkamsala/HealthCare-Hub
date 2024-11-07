package com.demo.exceptions;

public class ScheduleExistsException extends Exception{
    public ScheduleExistsException() {
    }

    public ScheduleExistsException(String message) {
        super(message);
    }

    public ScheduleExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleExistsException(Throwable cause) {
        super(cause);
    }

    public ScheduleExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
