package com.mkolongo.price_comparison.web.controllers;

import com.mkolongo.price_comparison.domain.models.binding.SellerRegisterModel;
import com.mkolongo.price_comparison.domain.models.service.SellerServiceModel;
import com.mkolongo.price_comparison.exception.SellerExistException;
import com.mkolongo.price_comparison.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;
    private final ModelMapper mapper;

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


        try {
            SellerServiceModel serviceModel = mapper.map(sellerRegisterModel, SellerServiceModel.class);
            sellerService.register(serviceModel);
        } catch (SellerExistException ex) {
            bindingResult.rejectValue("name", "error.sellerRegisterModel", ex.getMessage());
        }

        return "redirect:/seller/login";
    }

    @GetMapping("/home")
    public String home(Principal principal) {
        System.out.println(principal.getName());
        return "seller-home";
    }
}
