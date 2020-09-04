package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.models.binding.ProductEditModel;
import com.mkolongo.price_comparison.domain.models.service.ProductServiceModel;

import java.util.Set;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel serviceModel);

    Set<ProductServiceModel> getAll();

    ProductServiceModel getById(String id);

    void delete(String id);

    void edit(ProductEditModel editModel);

    Set<ProductServiceModel> getProductsByShopId(String shopId);
}
