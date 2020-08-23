package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.Address;
import com.mkolongo.product_shop.domain.entities.Product;
import com.mkolongo.product_shop.domain.entities.Shop;
import com.mkolongo.product_shop.domain.models.binding.ProductBindingModel;
import com.mkolongo.product_shop.domain.models.binding.ShopRegisterModel;
import com.mkolongo.product_shop.exception.ProductNotFoundException;
import com.mkolongo.product_shop.exception.SellerNotFoundException;
import com.mkolongo.product_shop.exception.ShopNotFoundException;
import com.mkolongo.product_shop.repositories.ProductRepository;
import com.mkolongo.product_shop.repositories.SellerRepository;
import com.mkolongo.product_shop.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        Shop shop = new Shop();
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
}
