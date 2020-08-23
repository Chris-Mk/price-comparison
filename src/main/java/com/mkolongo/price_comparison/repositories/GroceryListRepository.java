package com.mkolongo.price_comparison.repositories;

import com.mkolongo.price_comparison.domain.entities.GroceryList;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroceryListRepository extends BaseRepository<GroceryList> {

    Set<GroceryList> findByUser_Email(String user_email);
}
