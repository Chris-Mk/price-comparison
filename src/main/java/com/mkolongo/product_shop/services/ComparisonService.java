package com.mkolongo.product_shop.services;

public interface ComparisonService {
    
    void addProduct(String productId, String userEmail);

    void  removeProduct(String productId, String comparisonName);
}
