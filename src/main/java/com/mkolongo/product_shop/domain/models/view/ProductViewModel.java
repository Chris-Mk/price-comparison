package com.mkolongo.product_shop.domain.models.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductViewModel {

    private String productName;
    private String sellerName;
    private int quantity;
    private BigDecimal price;
}
