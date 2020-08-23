package com.mkolongo.price_comparison.exception;

public class PasswordsDontMatchException extends RuntimeException {

    public PasswordsDontMatchException(String message) {
        super(message);
    }
}
