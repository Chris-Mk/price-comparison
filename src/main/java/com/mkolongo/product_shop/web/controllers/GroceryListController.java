package com.mkolongo.product_shop.web.controllers;

import com.mkolongo.product_shop.domain.models.service.GroceryListServiceModel;
import com.mkolongo.product_shop.domain.models.view.GroceryListDetailsModel;
import com.mkolongo.product_shop.domain.models.view.ProductViewModel;
import com.mkolongo.product_shop.services.GroceryListService;
import com.mkolongo.product_shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/grocery-lists")
@RequiredArgsConstructor
public class GroceryListController {

    private final GroceryListService groceryListService;
    private final ProductService productService;
    private final ModelMapper mapper;

    @GetMapping("/my")
//    @PreAuthorize("isAuthenticated()")
    public String my(Model model, Principal principal) {
        var lists = groceryListService.getGroceryListsByOwnersEmail(principal.getName());
        model.addAttribute("model", lists);
        return "my-orders";
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String all(Model model) {
        model.addAttribute("model", groceryListService.getAllCompletedLists());
        return "all-orders";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String makeOrder(@PathVariable("id") String productId, Model model) {
        model.addAttribute("model", productService.getById(productId));
        return "order-product";
    }

    @PostMapping
    public String makeOrder(@RequestParam("id") String productId,
                            @RequestParam("quantity") byte quantity,
                            Principal principal) {
        groceryListService.addProduct(productId, principal.getName());
        return "redirect:/orders/my";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") String orderId, Model model) {
        final GroceryListDetailsModel byId = groceryListService.getGroceryListById(orderId);
        model.addAttribute("model", byId);
        return "order-details";
    }

    @ResponseBody
    @GetMapping("/fetch")
    @PreAuthorize("isAuthenticated()")
    public List<ProductViewModel> fetch(Principal principal) {
        final Set<GroceryListServiceModel> allOrders = groceryListService.getGroceryListsByOwnersEmail(principal.getName());
        return mapper.map(allOrders, new TypeToken<List<ProductViewModel>>() {}.getType());
    }
}
