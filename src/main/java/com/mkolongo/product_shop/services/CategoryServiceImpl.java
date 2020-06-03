package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.Category;
import com.mkolongo.product_shop.domain.models.service.CategoryServiceModel;
import com.mkolongo.product_shop.exception.CategoryExistException;
import com.mkolongo.product_shop.exception.CategoryNotFoundException;
import com.mkolongo.product_shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public String add(String name) {
        final Category category = new Category();
        category.setName(name);

        try {
            categoryRepository.saveAndFlush(category);
        } catch (Exception e) {
            throw new CategoryExistException("Category already exist!");
        }
        return name;
    }

    @Override
    public Set<CategoryServiceModel> getAll() {
        List<Category> all = categoryRepository.findAll();
        Set<CategoryServiceModel> categories =
                mapper.map(all, new TypeToken<Set<CategoryServiceModel>>() {}.getType());

        return categories.stream()
                .sorted(Comparator.comparing(CategoryServiceModel::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public CategoryServiceModel getById(String id) {
        return categoryRepository.findById(id)
                .map(category -> mapper.map(category, CategoryServiceModel.class))
                .orElseThrow(() -> new CategoryNotFoundException("Category with id <u>" + id + "</u> does not exist!"));
    }

    @Override
    public void editName(CategoryServiceModel serviceModel) {
        final String categoryId = serviceModel.getId();

        categoryRepository.findById(categoryId)
                .ifPresentOrElse(category -> {
                    category.setName(serviceModel.getName());
                    try {
                        categoryRepository.saveAndFlush(category);
                    } catch (Exception ex) {
                        throw new CategoryExistException("Category already exist!");
                    }
                }, () -> {
                    throw new CategoryNotFoundException("Category with id <u>" + categoryId + "</u> does not exist!");
                });
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }
}
