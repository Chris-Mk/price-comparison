package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.entities.Category;
import com.mkolongo.price_comparison.domain.entities.Product;
import com.mkolongo.price_comparison.domain.models.binding.ProductEditModel;
import com.mkolongo.price_comparison.domain.models.service.ProductServiceModel;
import com.mkolongo.price_comparison.exception.ProductNotFoundException;
import com.mkolongo.price_comparison.repositories.CategoryRepository;
import com.mkolongo.price_comparison.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public ProductServiceModel addProduct(ProductServiceModel serviceModel) {
        var categories = new HashSet<Category>();

        serviceModel.getCategories()
                .forEach(id -> categoryRepository.findById(id).ifPresent(categories::add));

        final Product product = mapper.map(serviceModel, Product.class);
//        product.setCategories(categories);

        return mapper.map(productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public Set<ProductServiceModel> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> mapper.map(product, ProductServiceModel.class))
                .sorted(Comparator.comparing(ProductServiceModel::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public ProductServiceModel getById(String id) {
        return productRepository.findById(id)
                .map(product -> mapper.map(product, ProductServiceModel.class))
                .orElseThrow(() -> new ProductNotFoundException("Product with id <u>" + id + "</u> does not exist!"));
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public void edit(ProductEditModel editModel) {
        final String productId = editModel.getId();

        productRepository.findById(productId)
                .ifPresentOrElse(product -> {

                    if (!editModel.getName().equals(product.getName())) {
                        product.setName(editModel.getName());
                    }

                    if (editModel.getPrice().compareTo(product.getPrice()) != 0) {
                        product.setPrice(editModel.getPrice());
                    }

                    if (!editModel.getDescription().equals(product.getDescription())) {
                        product.setDescription(editModel.getDescription());
                    }

//                    if (editModel.getCategories().size() != product.getCategories().size()) {
//                        var categories = new HashSet<Category>();
//
//                        editModel.getCategories()
//                                .forEach(cid -> categoryRepository.findById(cid)
//                                        .ifPresent(categories::add));
//
//                        product.setCategories(categories);
//                    }

                    productRepository.saveAndFlush(product);
                }, () -> {
                    throw new ProductNotFoundException("Failed to edit product with id <u>" + productId + "</u>");
                });
    }

    @Override
    public Set<ProductServiceModel> getByCategory(String categoryName) {
        final Set<Product> products = productRepository.findByCategory(categoryName);

        return mapper.map(products, new TypeToken<Set<ProductServiceModel>>() {}.getType());
//        final Category category = categoryRepository.findByName(categoryName);

//        return productRepository.findAll()
//                .stream()
//                .filter(product -> product.getCategories().contains(category))
//                .map(product -> mapper.map(product, ProductServiceModel.class))
//                .sorted(Comparator.comparing(ProductServiceModel::getName))
//                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
