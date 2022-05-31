package com.example.warehouse.exception;

public class ObjectNotFoundException extends BaseException {
    public ObjectNotFoundException() {
        super("Not found");
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String organization_not_found, String stackTrace) {
        super(organization_not_found, stackTrace);
    }
}
