package com.mkolongo.product_shop.web.controllers;

import com.mkolongo.product_shop.domain.models.binding.ShopRegisterModel;
import com.mkolongo.product_shop.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public String register(Model model) {
        model.addAttribute("shopRegisterModel", new ShopRegisterModel());
        return "";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("shopRegisterModel") ShopRegisterModel shopRegisterModel,
                           BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "";
        }

        shopService.createShop(shopRegisterModel, principal.getName());
        return "";
    }
}
