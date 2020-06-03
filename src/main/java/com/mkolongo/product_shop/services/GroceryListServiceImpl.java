package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.GroceryList;
import com.mkolongo.product_shop.domain.entities.Product;
import com.mkolongo.product_shop.domain.entities.User;
import com.mkolongo.product_shop.domain.models.service.GroceryListServiceModel;
import com.mkolongo.product_shop.domain.models.view.GroceryListDetailsModel;
import com.mkolongo.product_shop.exception.GroceryListExistException;
import com.mkolongo.product_shop.exception.GroceryListNotFoundException;
import com.mkolongo.product_shop.exception.ProductNotFoundException;
import com.mkolongo.product_shop.repositories.GroceryListRepository;
import com.mkolongo.product_shop.repositories.ProductRepository;
import com.mkolongo.product_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryListRepository groceryListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public GroceryList createList(String userEmail, String listName) {
        final User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User with username <u>" + userEmail + "</u> does not exist!"));

        user.getGroceryLists()
                .forEach(groceryList -> {
                    if (groceryList.getName().equals(listName)) {
                        throw new GroceryListExistException("Grocery list with name <u>" + listName + "</u> already exists!");
                    }
                });

        GroceryList list = new GroceryList();
        list.setCreatedAt(LocalDateTime.now());
        list.setCompleted(false);
        list.setName(listName);
        list.setUser(user);

        return groceryListRepository.save(list);
    }

    @Override
    public void addProduct(String listId, String productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id <u>" + productId + "</u> does not exist!"));

        groceryListRepository
                .findById(listId)
                .ifPresentOrElse(groceryList -> {
                    groceryList.getProducts().add(product);
                    groceryListRepository.save(groceryList);
                }, () -> {
                    throw new GroceryListNotFoundException();
                });
    }

    @Override
    public GroceryListDetailsModel getGroceryListById(String listId) {
        return groceryListRepository.findById(listId)
                .map(groceryList -> mapper.map(groceryList, GroceryListDetailsModel.class))
                .orElseThrow(() -> {
                    throw new GroceryListNotFoundException("Order with id <u>" + listId + "</u> does not exist!");
                });
    }

    @Override
    public Set<GroceryListServiceModel> getGroceryListsByOwnersEmail(String ownerEmail) {
        return mapper.map(groceryListRepository.findByUser_Email(ownerEmail),
                new TypeToken<Set<GroceryListServiceModel>>() {}.getType());
    }

    @Override
    public Set<GroceryListServiceModel> getAllCompletedLists() {
        List<GroceryList> completedLists = groceryListRepository.findAll()
                .stream()
                .filter(GroceryList::isCompleted)
                .collect(Collectors.toList());

        return mapper.map(completedLists, new TypeToken<Set<GroceryListServiceModel>>() {}.getType());
    }

    @Override
    public Set<GroceryListServiceModel> getAllUncompletedLists() {
        List<GroceryList> completedLists = groceryListRepository.findAll()
                .stream()
                .filter(groceryList -> !groceryList.isCompleted())
                .collect(Collectors.toList());

        return mapper.map(completedLists, new TypeToken<Set<GroceryListServiceModel>>() {}.getType());
    }

    @Override
    public void removeProduct(String listId, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        groceryListRepository.findById(listId)
                .ifPresent(groceryList -> groceryList.getProducts().remove(product));
    }
}
