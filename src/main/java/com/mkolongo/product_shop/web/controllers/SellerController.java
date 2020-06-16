package com.mkolongo.product_shop.web.controllers;

import com.mkolongo.product_shop.domain.models.binding.SellerRegisterModel;
import com.mkolongo.product_shop.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/login")
    public String login() {
        return "seller-login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("sellerRegisterModel", new SellerRegisterModel());
        return "seller-register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("sellerRegisterModel") SellerRegisterModel sellerRegisterModel,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "seller-register";

        sellerService.register(sellerRegisterModel);
        return "redirect:/sellers/login";
    }
}
