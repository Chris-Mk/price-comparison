package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.models.service.GroceryListServiceModel;
import com.mkolongo.price_comparison.domain.models.view.GroceryListDetailsModel;

import java.util.Set;

public interface GroceryListService {

    void createList(String listName, String userEmail);

    void addProduct(String listId, String productId);

    GroceryListDetailsModel getGroceryListById(String id);

    Set<GroceryListServiceModel> getGroceryListsByOwnersEmail(String customerName);

    Set<GroceryListServiceModel> getAllCompletedLists();

    Set<GroceryListServiceModel> getAllUncompletedLists();

    void removeProduct(String listId, String productId);
}
