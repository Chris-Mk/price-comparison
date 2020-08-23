package com.mkolongo.price_comparison.domain.models.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserEditModel {

    private String id;

    @NotBlank(message = "Invalid username!")
    private String username;

    @NotBlank(message = "Old Password required!")
    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;

    @Email(regexp = "^\\w+[.\\w]*@\\w+[.\\w]*", message = "Invalid email address!")
    private String email;
}
