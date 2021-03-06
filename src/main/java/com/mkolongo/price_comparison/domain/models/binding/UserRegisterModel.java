package com.mkolongo.price_comparison.domain.models.binding;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterModel {

    @NotBlank(message = "First name required!")
    @Length(min = 2, message = "First name must be at least 2 characters long!")
    private String firstName;

    @NotBlank(message = "Last name required!")
    @Length(min = 2, message = "Last name must be at least 2 characters long!")
    private String lastName;

    @NotBlank(message = "Password required!")
    @Length(min = 6, message = "Password must be at least 6 characters long!")
    private String password;

    @Email(regexp = "^\\w+[.\\w]*@\\w+[.\\w]*", message = "Invalid email address!")
    private String email;
}
