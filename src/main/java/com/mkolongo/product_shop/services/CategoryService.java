package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.models.service.CategoryServiceModel;

import java.util.Set;

public interface CategoryService {

    String add(String name);

    Set<CategoryServiceModel> getAll();

    CategoryServiceModel getById(String id);

    void delete(String id);

    void editName(CategoryServiceModel serviceModel);
}
