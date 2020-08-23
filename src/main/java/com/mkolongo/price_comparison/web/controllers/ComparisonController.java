package com.mkolongo.price_comparison.web.controllers;

import com.mkolongo.price_comparison.domain.models.view.ComparisonViewModel;
import com.mkolongo.price_comparison.services.ComparisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/my-comparisons")
@RequiredArgsConstructor
public class ComparisonController {

    private final ComparisonService comparisonService;
//    private final GroceryListService groceryListService;

    @PostMapping("/add-product/{productId}")
    public void addProduct(@PathVariable String productId, Principal principal) {
        comparisonService.addProduct(productId, principal.getName());
    }

    @DeleteMapping("{comparisonId}/remove-product/{productId}")
    public void removeProduct(@PathVariable String comparisonId, @PathVariable String productId) {
        comparisonService.removeProduct(comparisonId, productId);
    }

    @GetMapping
    public void myComparisons(Model model, Principal principal) {
        Set<ComparisonViewModel> myComparisons = comparisonService.getAllComparisons(principal.getName());
        model.addAttribute("myComparisons", myComparisons);
    }

    @DeleteMapping("/delete-comparison/{comparisonId}")
    public void deleteComparison(@PathVariable String comparisonId) {
        comparisonService.deleteComparison(comparisonId);
    }

    @GetMapping("/details/{comparisonId}")
    public String details(@PathVariable String comparisonId, Model model) {
        ComparisonViewModel comparisonViewModel = comparisonService.getComparisonById(comparisonId);
        model.addAttribute("comparisonViewModel", comparisonViewModel);

//        var orders = groceryListService.getGroceryListsByOwnersEmail(principal.getName());
//        final BigDecimal totalPrice = orders.stream()
//                .map(o -> o.getTotalPrice().multiply(BigDecimal.valueOf(o.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        model.addAttribute("model", orders);
//        model.addAttribute("price", totalPrice);

        return "cart-details";
    }

//    @PostMapping("/checkout")
//    public String checkout() {
//        return "redirect:/users/home";
//    }
//
//    @PostMapping("/remove/{id}")
//    public String remove(@PathVariable String id) {
////        groceryListService.removeProduct(id);
//        return "redirect:/cart/details";
//    }
}
