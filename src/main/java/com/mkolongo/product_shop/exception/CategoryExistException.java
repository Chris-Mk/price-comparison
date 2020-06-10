package com.mkolongo.product_shop.exception;

public class CategoryExistException extends RuntimeException {

    public CategoryExistException(String message) {
        super(message);
    }
}
