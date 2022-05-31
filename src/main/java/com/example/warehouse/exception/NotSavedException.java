package com.example.warehouse.exception;

public class NotSavedException extends BaseException {
    public NotSavedException(String message) {
        super(message);
    }

    public NotSavedException(String message, String stackTrace) {
        super(message, stackTrace);
    }
}
