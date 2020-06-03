package com.mkolongo.product_shop.domain.models.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserViewModel {

    private String id;
    private String username;
    private String email;
    private Set<String> roles;
}
