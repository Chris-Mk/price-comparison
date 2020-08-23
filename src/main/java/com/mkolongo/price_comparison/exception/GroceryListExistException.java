package com.mkolongo.price_comparison.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroceryListExistException extends RuntimeException {

    public GroceryListExistException(String message) {
        super(message);
    }
}
