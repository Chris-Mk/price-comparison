package com.mkolongo.product_shop.web.controllers;

import com.mkolongo.product_shop.domain.entities.GroceryList;
import com.mkolongo.product_shop.domain.models.view.GroceryListDetailsModel;
import com.mkolongo.product_shop.services.GroceryListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/my-lists")
@RequiredArgsConstructor
public class GroceryListController {

    private final GroceryListService groceryListService;
//    private final ProductService productService;
//    private final ModelMapper mapper;

    @PostMapping("/new/{listName}")
    public GroceryList newList(@PathVariable String listName, Principal principal) {
        return groceryListService.createList(listName, principal.getName());
    }

    @PostMapping("/{listId}/add-product/{productId}")
    public void addProduct(@PathVariable String listId, @PathVariable String productId) {
        groceryListService.addProduct(listId, productId);
    }

    @DeleteMapping("/{listId}/remove-product/{productId}")
    public void removeProduct(@PathVariable String listId, @PathVariable String productId) {
        groceryListService.removeProduct(listId, productId);
    }

    @GetMapping
//    @PreAuthorize("isAuthenticated()")
    public String myLists(Model model, Principal principal) {
        var lists = groceryListService.getGroceryListsByOwnersEmail(principal.getName());
        model.addAttribute("model", lists);
        return "my-lists";
    }


    @GetMapping("/details/{listId}")
    public String details(@PathVariable String listId, Model model) {
        final GroceryListDetailsModel list = groceryListService.getGroceryListById(listId);
        model.addAttribute("model", list);
        return "list-details";
    }

//    @GetMapping("/all")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
//    public String all(Model model) {
//        model.addAttribute("model", groceryListService.getAllCompletedLists());
//        return "all-orders";
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public String makeOrder(@PathVariable("id") String productId, Model model) {
//        model.addAttribute("model", productService.getById(productId));
//        return "order-product";
//    }
//
//    @PostMapping
//    public String makeOrder(@RequestParam("id") String productId,
//                            @RequestParam("quantity") byte quantity,
//                            Principal principal) {
//        groceryListService.addProduct(productId, principal.getName());
//        return "redirect:/orders/my";
//    }
//
//    @ResponseBody
//    @GetMapping("/fetch")
//    @PreAuthorize("isAuthenticated()")
//    public List<ProductViewModel> fetch(Principal principal) {
//        final Set<GroceryListServiceModel> allOrders = groceryListService.getGroceryListsByOwnersEmail(principal.getName());
//        return mapper.map(allOrders, new TypeToken<List<ProductViewModel>>() {}.getType());
//    }
}
