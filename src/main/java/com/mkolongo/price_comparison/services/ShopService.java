package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.models.binding.ProductBindingModel;
import com.mkolongo.price_comparison.domain.models.binding.ShopRegisterModel;
import com.mkolongo.price_comparison.domain.models.view.ShopViewModel;

import java.util.Set;

public interface ShopService {

    void createShop(ShopRegisterModel shopRegisterModel, String sellerName);

    void addProduct(String shopId, ProductBindingModel productBindingModel);

    void removeProduct(String shopId, String productId);

    Set<ShopViewModel> getShopsBySellerName(String sellerName);
}
