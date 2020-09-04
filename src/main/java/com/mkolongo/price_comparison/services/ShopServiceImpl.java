package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.entities.Address;
import com.mkolongo.price_comparison.domain.entities.Product;
import com.mkolongo.price_comparison.domain.entities.Shop;
import com.mkolongo.price_comparison.domain.models.binding.ProductBindingModel;
import com.mkolongo.price_comparison.domain.models.binding.ShopRegisterModel;
import com.mkolongo.price_comparison.domain.models.view.ShopViewModel;
import com.mkolongo.price_comparison.exception.ProductNotFoundException;
import com.mkolongo.price_comparison.exception.SellerNotFoundException;
import com.mkolongo.price_comparison.exception.ShopNotFoundException;
import com.mkolongo.price_comparison.repositories.ProductRepository;
import com.mkolongo.price_comparison.repositories.SellerRepository;
import com.mkolongo.price_comparison.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper mapper;

    @Override
    public void createShop(ShopRegisterModel shopRegisterModel, String sellerName) {
        Address address = mapper.map(shopRegisterModel, Address.class);
        Shop shop = mapper.map(shopRegisterModel, Shop.class);

        shop.setCreatedAt(LocalDateTime.now());
        shop.setAddress(address);

        sellerRepository.findByName(sellerName)
                .ifPresentOrElse(seller -> {
                    shop.setSeller(seller);
                    shopRepository.save(shop);
                }, SellerNotFoundException::new);

    }

    @Override
    public void addProduct(String shopId, ProductBindingModel productBindingModel) {
        final Product product = productRepository.save(mapper.map(productBindingModel, Product.class));

        shopRepository.findById(shopId)
                .ifPresentOrElse(shop -> {
                    shop.getProducts().add(product);
                    shopRepository.save(shop);
                }, ShopNotFoundException::new);
    }

    @Override
    public void removeProduct(String shopId, String productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id <u>" + productId + "</u> does not exist!"));

        shopRepository.findById(shopId)
                .ifPresent(shop -> shop.getProducts().remove(product));
    }

    @Override
    public Set<ShopViewModel> getShopsBySellerName(String sellerName) {
        Set<Shop> shops = shopRepository.findBySeller_Name(sellerName)
                .stream()
                .sorted(Comparator.comparing(Shop::getCreatedAt))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return mapper.map(shops, new TypeToken<Set<ShopViewModel>>() {}.getType());
    }
}
