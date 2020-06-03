package com.mkolongo.product_shop.exception;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException() {
    }

    public ShopNotFoundException(String message) {
        super(message);
    }
}
