package com.mkolongo.price_comparison.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
