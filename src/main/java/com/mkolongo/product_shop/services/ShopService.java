package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.models.binding.ProductBindingModel;
import com.mkolongo.product_shop.domain.models.binding.ShopRegisterModel;

public interface ShopService {

    void createShop(ShopRegisterModel shopRegisterModel, String sellerName);

    void addProduct(String shopId, ProductBindingModel productBindingModel);

}
