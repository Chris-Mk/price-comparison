package com.mkolongo.price_comparison.domain.models.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroceryListDetailsModel {

    private String id;
    private String listName;
    private String createdAt;
    private Set<ProductViewModel> products;

}
