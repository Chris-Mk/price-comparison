package com.mkolongo.price_comparison.domain.models.service;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductServiceModel {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Set<String> categories;

}
