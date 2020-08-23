package com.mkolongo.price_comparison.domain.models.binding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopRegisterModel {

    private String name;
    private String country;
    private String city;
    private String street;
    private int number;
}
