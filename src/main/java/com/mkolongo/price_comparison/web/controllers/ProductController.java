package com.mkolongo.price_comparison.web.controllers;

import com.mkolongo.price_comparison.domain.models.binding.ProductBindingModel;
import com.mkolongo.price_comparison.domain.models.binding.ProductEditModel;
import com.mkolongo.price_comparison.domain.models.service.ProductServiceModel;
import com.mkolongo.price_comparison.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ModelMapper mapper;

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String add(Model model) {
        model.addAttribute("model", new ProductBindingModel());
        return "add-product";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("model") ProductBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-product";
        }

        productService.addProduct(mapper.map(bindingModel, ProductServiceModel.class));
        return "redirect:/products/all";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String all(Model model) {
        model.addAttribute("products", productService.getAll());
        return "all-products";
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public String details(@PathVariable("id") String productId, Model model) {
        model.addAttribute("model", productService.getById(productId));
        return "details";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String edit(@PathVariable("id") String productId, Model model) {
        model.addAttribute("model", productService.getById(productId));
        return "edit-product";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid @ModelAttribute("model") ProductEditModel editModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-product";
        }

        productService.edit(editModel);
        return "redirect:/products/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String delete(@PathVariable String id, Model model) {
        model.addAttribute("model", productService.getById(id));
        return "delete-product";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.delete(id);
        return "redirect:/products/all";
    }

    @ResponseBody
    @GetMapping("/fetch/{category}")
    @PreAuthorize("isAuthenticated()")
    public Set<ProductServiceModel> fetch(@PathVariable String category) {
        return category.equals("all") ? productService.getAll() : productService.getByCategory(category);
    }

}
