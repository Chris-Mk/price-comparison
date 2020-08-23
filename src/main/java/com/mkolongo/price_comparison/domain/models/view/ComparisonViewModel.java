package com.mkolongo.price_comparison.domain.models.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ComparisonViewModel {

    private String name;
    private Set<ProductViewModel> products;

}
