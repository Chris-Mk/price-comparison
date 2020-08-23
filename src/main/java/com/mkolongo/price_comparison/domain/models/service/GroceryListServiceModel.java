package com.mkolongo.price_comparison.domain.models.service;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroceryListServiceModel {

    private String id;
    private String name;
    private LocalDateTime createdAt;
    //    private String imageUrl;
    //    private String customerName;
    //    private BigDecimal totalPrice;
//    private String productDescription;
//    private int quantity;

}
