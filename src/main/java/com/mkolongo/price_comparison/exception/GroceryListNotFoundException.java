package com.mkolongo.price_comparison.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroceryListNotFoundException extends RuntimeException {

    public GroceryListNotFoundException(String message) {
        super(message);
    }
}
