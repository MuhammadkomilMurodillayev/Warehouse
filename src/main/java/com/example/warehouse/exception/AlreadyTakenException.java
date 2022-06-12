package com.example.warehouse.exception;

public class AlreadyTakenException extends BaseException {
    public AlreadyTakenException(String element) {
        super(element + " already taken");
    }
}
