package com.mkolongo.price_comparison.exception;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException() {
    }

    public ShopNotFoundException(String message) {
        super(message);
    }
}
