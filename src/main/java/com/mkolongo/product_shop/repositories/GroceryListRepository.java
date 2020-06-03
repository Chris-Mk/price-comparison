package com.mkolongo.product_shop.repositories;

import com.mkolongo.product_shop.domain.entities.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroceryListRepository extends BaseRepository<GroceryList> {

    Set<GroceryList> findByUser_Email(String user_email);
}
