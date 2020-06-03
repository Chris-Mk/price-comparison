package com.mkolongo.product_shop.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroceryListNotFoundException extends RuntimeException {

    public GroceryListNotFoundException(String message) {
        super(message);
    }
}
