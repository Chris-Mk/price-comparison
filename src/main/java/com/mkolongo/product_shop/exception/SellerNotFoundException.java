package com.mkolongo.product_shop.exception;

public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException() {
    }

    public SellerNotFoundException(String message) {
        super(message);
    }
}
