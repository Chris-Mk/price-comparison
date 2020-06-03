package com.mkolongo.product_shop.domain.models.binding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginBindingModel {

    private String username;
    private String password;
}
