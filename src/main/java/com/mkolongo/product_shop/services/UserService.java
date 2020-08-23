package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.Role;
import com.mkolongo.product_shop.domain.models.binding.UserEditModel;
import com.mkolongo.product_shop.domain.models.service.UserServiceModel;
import com.mkolongo.product_shop.domain.models.view.UserProfileModel;
import com.mkolongo.product_shop.domain.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(UserServiceModel serviceModel);

    List<UserViewModel> getAllUsers();

    void editRole(String id, Role role);

    void editUser(UserEditModel bindingModel);

    UserEditModel getUserById(String id);

    UserProfileModel getUserByEmail(String username);
}
