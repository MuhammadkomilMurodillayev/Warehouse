package com.example.warehouse.exception;

public class FileNotFoundException extends BaseException {
    public FileNotFoundException(String message, String stackTrace) {
        super(message, stackTrace);
    }
}
