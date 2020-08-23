package com.mkolongo.price_comparison.exception;

public class CategoryExistException extends RuntimeException {

    public CategoryExistException(String message) {
        super(message);
    }
}
