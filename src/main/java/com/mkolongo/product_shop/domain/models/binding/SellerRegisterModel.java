package com.mkolongo.product_shop.domain.models.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SellerRegisterModel {

    @NotBlank(message = "Company name required!")
    private String name;

    @Email(regexp = "", message = "Company email required!")
    private String email;

    @NotBlank(message = "Password required!")
    private String password;

}
