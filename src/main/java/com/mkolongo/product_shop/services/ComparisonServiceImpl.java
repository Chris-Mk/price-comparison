package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.Comparison;
import com.mkolongo.product_shop.domain.entities.Product;
import com.mkolongo.product_shop.domain.entities.User;
import com.mkolongo.product_shop.exception.ProductNotFoundException;
import com.mkolongo.product_shop.exception.UsernameExistException;
import com.mkolongo.product_shop.repositories.ComparisonRepository;
import com.mkolongo.product_shop.repositories.ProductRepository;
import com.mkolongo.product_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComparisonServiceImpl implements ComparisonService {

    private final ComparisonRepository comparisonRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void addProduct(String productId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameExistException(""));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(""));

        String category = product.getCategory().getName();

        comparisonRepository.findByName(category)
                .ifPresentOrElse(comparison -> comparison.getProducts().add(product),
                        () -> {
                            Comparison comparison = new Comparison();
                            comparison.setUser(user);
                            comparison.setName(category);
                            comparison.getProducts().add(product);

                            comparisonRepository.save(comparison);
                        });
    }

    @Override
    public void removeProduct(String productId, String comparisonName) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->  new ProductNotFoundException(""));

        comparisonRepository.findByName(comparisonName)
                .ifPresent(comparison -> comparison.getProducts().remove(product));
    }
}
