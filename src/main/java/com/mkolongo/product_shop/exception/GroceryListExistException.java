package com.mkolongo.product_shop.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroceryListExistException extends RuntimeException {

    public GroceryListExistException(String message) {
        super(message);
    }
}
