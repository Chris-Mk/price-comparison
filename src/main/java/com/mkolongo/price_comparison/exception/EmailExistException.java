package com.mkolongo.price_comparison.exception;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String message) {
        super(message);
    }
}
