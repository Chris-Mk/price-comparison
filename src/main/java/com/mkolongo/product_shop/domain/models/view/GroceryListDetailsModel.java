package com.mkolongo.product_shop.domain.models.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class GroceryListDetailsModel {

    private String id;
    private String listName;
    private String createdAt;
    private Set<ProductViewModel> products;

}
