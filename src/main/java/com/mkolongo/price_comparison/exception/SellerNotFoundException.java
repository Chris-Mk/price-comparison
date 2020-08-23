package com.mkolongo.price_comparison.exception;

public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException() {
    }

    public SellerNotFoundException(String message) {
        super(message);
    }
}
