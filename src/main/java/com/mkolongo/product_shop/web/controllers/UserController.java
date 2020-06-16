package com.mkolongo.product_shop.web.controllers;

import com.mkolongo.product_shop.domain.entities.Role;
import com.mkolongo.product_shop.domain.models.binding.UserEditModel;
import com.mkolongo.product_shop.domain.models.binding.UserRegisterModel;
import com.mkolongo.product_shop.domain.models.service.CategoryServiceModel;
import com.mkolongo.product_shop.domain.models.service.UserServiceModel;
import com.mkolongo.product_shop.domain.models.view.UserProfileModel;
import com.mkolongo.product_shop.exception.EmailExistException;
import com.mkolongo.product_shop.exception.InvalidPassword;
import com.mkolongo.product_shop.exception.PasswordsDontMatchException;
import com.mkolongo.product_shop.exception.UsernameExistException;
import com.mkolongo.product_shop.services.CategoryService;
import com.mkolongo.product_shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping("/login")
    public String login() {
        return "user-login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRegisterModel", new UserRegisterModel());
        return "user-register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userRegisterModel") UserRegisterModel bindingModel,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user-register";

        try {
            UserServiceModel serviceModel = mapper.map(bindingModel, UserServiceModel.class);
            userService.register(serviceModel);
        } catch (UsernameExistException | EmailExistException | PasswordsDontMatchException ex) {
            if (ex instanceof UsernameExistException) {
                bindingResult.rejectValue("username", "error.bindingModel", ex.getMessage());
            } else if (ex instanceof EmailExistException) {
                bindingResult.rejectValue("email", "error.bindingModel", ex.getMessage());
            } else {
                bindingResult.rejectValue("password", "error.bindingModel", ex.getMessage());
                bindingResult.rejectValue("confirmPassword", "error.bindingModel", ex.getMessage());
            }
            return "user-register";
        }

        return "redirect:/users/login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        final Set<String> categories = categoryService.getAll()
                .stream()
                .map(CategoryServiceModel::getName)
                .collect(Collectors.toSet());

        model.addAttribute("categories", categories);
        return "home";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Principal principal, Model model) {
        final UserProfileModel user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable("id") String userId, Model model) {
        final UserEditModel bindingModel = userService.getUserById(userId);
        model.addAttribute("user", bindingModel);
        return "edit-profile";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid @ModelAttribute("user") UserEditModel editModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-profile";
        }

        try {
            userService.editUser(editModel);
        } catch (PasswordsDontMatchException | EmailExistException | InvalidPassword | UsernameExistException ex) {
            if (ex instanceof EmailExistException) {
                bindingResult.rejectValue("email", "error.editModel", ex.getMessage());
            } else if (ex instanceof InvalidPassword) {
                bindingResult.rejectValue("oldPassword", "error.editModel", ex.getMessage());
            } else if (ex instanceof UsernameExistException) {
                bindingResult.rejectValue("username", "error.editModel", ex.getMessage());
            } else {
                bindingResult.rejectValue("newPassword", "error.editModel", ex.getMessage());
                bindingResult.rejectValue("confirmNewPassword", "error.editModel", ex.getMessage());
            }
            return "edit-profile";
        }

        return "redirect:/users/profile";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }

//    @PostMapping("/set-moderator/{id}")
//    public String setModerator(@PathVariable("id") String userId) {
//        userService.editRole(userId, Role.ROLE_MODERATOR);
//        return "redirect:/users/all";
//    }

    @PostMapping("/set-admin/{id}")
    public String setAdmin(@PathVariable("id") String userId) {
        userService.editRole(userId, Role.ROLE_ADMIN);
        return "redirect:/users/all";
    }

    @PostMapping("/set-user/{id}")
    public String setUser(@PathVariable("id") String userId) {
        userService.editRole(userId, Role.ROLE_USER);
        return "redirect:/users/all";
    }
}
