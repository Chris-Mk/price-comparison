package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.models.binding.ProductBindingModel;
import com.mkolongo.product_shop.domain.models.binding.SellerRegisterModel;
import com.mkolongo.product_shop.domain.models.binding.ShopRegisterModel;

public interface SellerService {

    void register(SellerRegisterModel sellerRegisterModel);

    void createShop(ShopRegisterModel shopRegisterModel, String sellerName);

    void addProductToShop(String shopId, ProductBindingModel productBindingModel);
}
