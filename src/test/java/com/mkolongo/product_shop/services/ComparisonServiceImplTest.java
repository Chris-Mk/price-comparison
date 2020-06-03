package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.Category;
import com.mkolongo.product_shop.domain.entities.Comparison;
import com.mkolongo.product_shop.domain.entities.Product;
import com.mkolongo.product_shop.domain.entities.User;
import com.mkolongo.product_shop.repositories.ComparisonRepository;
import com.mkolongo.product_shop.repositories.ProductRepository;
import com.mkolongo.product_shop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComparisonServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    ComparisonRepository comparisonRepository;
    @InjectMocks
    ComparisonServiceImpl comparisonService;

    User user;
    Product product;
    Comparison comparison;

    @BeforeEach
    void setUp() {
        user = new User() {{
            setId("testId");
            setEmail("test@test.test");
        }};

//        category = new Category() {{
//            setName("test name");
//        }};

        comparison = new Comparison() {{
            setUser(user);
            setProducts(new HashSet<>());
        }};

        product = new Product() {{
            setId("testId");
            setCategory(new Category() {{
                setName("test name");
            }});
        }};
    }

    @Test
    void addProduct_withValidInput_worksFine() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(comparisonRepository.findByName(product.getCategory().getName())).thenReturn(Optional.ofNullable(comparison));

        comparisonService.addProduct(product.getId(), user.getEmail());

        assertEquals(1, comparison.getProducts().size());
    }
}