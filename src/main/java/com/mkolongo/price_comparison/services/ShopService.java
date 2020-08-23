package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.models.binding.ProductBindingModel;
import com.mkolongo.price_comparison.domain.models.binding.ShopRegisterModel;

public interface ShopService {

    void createShop(ShopRegisterModel shopRegisterModel, String sellerName);

    void addProduct(String shopId, ProductBindingModel productBindingModel);

    void removeProduct(String shopId, String productId);
}
