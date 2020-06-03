package com.mkolongo.product_shop.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
