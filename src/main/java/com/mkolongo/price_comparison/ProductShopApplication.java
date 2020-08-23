package com.mkolongo.price_comparison;

import com.mkolongo.price_comparison.domain.JwtPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableConfigurationProperties(JwtPropertiesConfig.class)
@SpringBootApplication
public class ProductShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductShopApplication.class, args);
    }

}

