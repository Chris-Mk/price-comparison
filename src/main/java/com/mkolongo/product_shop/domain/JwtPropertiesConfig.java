package com.mkolongo.product_shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtPropertiesConfig {

    private String tokenPrefix;
    private String secretKey;
    private long tokenExpirationDays;

    public String authorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
