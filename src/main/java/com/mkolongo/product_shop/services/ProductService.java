package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.models.binding.ProductEditModel;
import com.mkolongo.product_shop.domain.models.service.ProductServiceModel;

import java.util.Set;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel serviceModel);

    Set<ProductServiceModel> getAll();

    ProductServiceModel getById(String id);

    void delete(String id);

    void edit(ProductEditModel editModel);

    Set<ProductServiceModel> getByCategory(String category);
}
