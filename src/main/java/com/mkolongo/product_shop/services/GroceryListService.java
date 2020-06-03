package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.GroceryList;
import com.mkolongo.product_shop.domain.models.service.GroceryListServiceModel;
import com.mkolongo.product_shop.domain.models.view.GroceryListDetailsModel;

import java.util.Set;

public interface GroceryListService {

    GroceryList createList(String listName, String userEmail);

    void addProduct(String productId, String listId);

    GroceryListDetailsModel getGroceryListById(String id);

    Set<GroceryListServiceModel> getGroceryListsByOwnersEmail(String customerName);

    Set<GroceryListServiceModel> getAllCompletedLists();

    Set<GroceryListServiceModel> getAllUncompletedLists();

    void removeProduct(String listId, String productId);
}