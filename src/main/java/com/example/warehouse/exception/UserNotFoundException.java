package com.example.warehouse.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("User not found");
    }

}
