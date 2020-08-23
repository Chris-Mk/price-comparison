package com.mkolongo.price_comparison.web.controllers;

import com.mkolongo.price_comparison.domain.models.service.CategoryServiceModel;
import com.mkolongo.price_comparison.exception.CategoryExistException;
import com.mkolongo.price_comparison.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN, MODERATOR')")
    public String add() {
        return "add-category";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, Model model) {
        if (StringUtils.isEmpty(name)) {
            model.addAttribute("err", "Category name cannot be empty!");
            return "add-category";
        }

        try {
            categoryService.add(name);
        } catch (CategoryExistException ex) {
            model.addAttribute("err", ex.getMessage());
            return "add-category";
        }

        return "redirect:/categories/all";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String all(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "all-categories";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("model", categoryService.getById(id));
        return "edit-category";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid @ModelAttribute("model") CategoryServiceModel serviceModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-category";
        }

        categoryService.editName(serviceModel);
        return "redirect:/categories/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String delete(@PathVariable String id, Model model) {
        model.addAttribute("model", categoryService.getById(id));
        return "delete-category";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        categoryService.delete(id);
        return "redirect:/categories/all";
    }

    @ResponseBody
    @GetMapping("/fetch")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Set<CategoryServiceModel> fetch() {
        return categoryService.getAll();
    }
}
