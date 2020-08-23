package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.entities.Role;
import com.mkolongo.price_comparison.domain.models.binding.UserEditModel;
import com.mkolongo.price_comparison.domain.models.service.UserServiceModel;
import com.mkolongo.price_comparison.domain.models.view.UserProfileModel;
import com.mkolongo.price_comparison.domain.models.view.UserViewModel;
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
