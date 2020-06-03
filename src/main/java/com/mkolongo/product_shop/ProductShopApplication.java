package com.mkolongo.product_shop;

import com.mkolongo.product_shop.domain.JwtPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtPropertiesConfig.class)
@SpringBootApplication
public class ProductShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductShopApplication.class, args);
    }

}

