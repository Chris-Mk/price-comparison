package com.mkolongo.price_comparison.domain.models.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductEditModel {

    private String id;

    @NotBlank(message = "Invalid name!")
    private String name;

    private String description;

    @NotNull(message = "Price required!")
    @Digits(integer = 10, fraction = 2)
    @Min(value = 0, message = "Price cannot be negative!")
    private BigDecimal price;

    //    @NotNull(message = "Image URL required!")
    private String imageUrl;

    @Size(min = 1, message = "Choice at least one category!")
    private Set<String> categories;

}
