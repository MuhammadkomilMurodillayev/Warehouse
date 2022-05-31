package com.example.warehouse.exception;

public class PermissionDenied extends BaseException {
    public PermissionDenied() {
        super("Permission denied");
    }
    public PermissionDenied(String message) {
        super(message);
    }
}
