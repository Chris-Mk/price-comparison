package com.mkolongo.price_comparison.domain.models.service;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CategoryServiceModel {

    private String id;

    @NotBlank(message = "Invalid name!")
    private String name;

}
