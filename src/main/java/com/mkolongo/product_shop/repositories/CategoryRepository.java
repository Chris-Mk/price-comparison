package com.mkolongo.product_shop.repositories;

import com.mkolongo.product_shop.domain.entities.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    Category findByName(String name);
}
