package com.mkolongo.price_comparison.domain.models.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ShopViewModel {

    private String id;
    private String name;
    private String country;
    private String city;
    private String street;
    private int streetNumber;
    private LocalTime openingTime;
    private LocalTime closingTime;

}
