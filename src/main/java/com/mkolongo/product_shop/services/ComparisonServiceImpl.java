package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.Comparison;
import com.mkolongo.product_shop.domain.entities.Product;
import com.mkolongo.product_shop.domain.entities.User;
import com.mkolongo.product_shop.domain.models.view.ComparisonViewModel;
import com.mkolongo.product_shop.exception.ComparisonNotExistException;
import com.mkolongo.product_shop.exception.ProductNotFoundException;
import com.mkolongo.product_shop.repositories.ComparisonRepository;
import com.mkolongo.product_shop.repositories.ProductRepository;
import com.mkolongo.product_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ComparisonServiceImpl implements ComparisonService {

    private final ComparisonRepository comparisonRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public void addProduct(String productId, String userEmail) {
        final User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with username <u>" + userEmail + "</u> does not exist!"));

        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id <u>" + productId + "</u> does not exist!"));

        String category = product.getCategory().getName();
        comparisonRepository.findByName(category)
                .ifPresentOrElse(comparison -> comparison.getProducts().add(product),
                        () -> {
                            Comparison comparison = new Comparison();
                            comparison.setUser(user);
                            comparison.setName(category);
                            comparison.getProducts().add(product);

//                            comparison.setProducts(new HashSet<>());
//                            comparison.getProducts().add(product);

                            comparisonRepository.save(comparison);
                        });
    }

    @Override
    public void removeProduct(String comparisonId, String productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id <u>" + productId + "</u> does not exist!"));

        comparisonRepository.findById(comparisonId)
                .ifPresent(comparison -> {
                    comparison.getProducts().remove(product);

                    if (comparison.getProducts().isEmpty())
                        comparisonRepository.deleteById(comparisonId);
                });
    }

    @Override
    public void deleteComparison(String comparisonId) {
        comparisonRepository.deleteById(comparisonId);
    }

    @Override
    public ComparisonViewModel getComparisonById(String comparisonId) {
        return comparisonRepository.findById(comparisonId)
                .map(comparison -> mapper.map(comparison, ComparisonViewModel.class))
                .orElseThrow(() -> new ComparisonNotExistException(
                        "Comparison with id <u>" + comparisonId + "</u> does not exist!"));
    }

    @Override
    public Set<ComparisonViewModel> getAllComparisons(String userEmail) {
        return mapper.map(comparisonRepository.findByUser_Email(userEmail),
                new TypeToken<Set<ComparisonViewModel>>() {}.getType());
    }
}
