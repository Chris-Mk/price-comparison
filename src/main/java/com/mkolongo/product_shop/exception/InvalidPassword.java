package com.mkolongo.product_shop.exception;

public class InvalidPassword extends RuntimeException {

    public InvalidPassword(String message) {
        super(message);
    }
}
