package com.mkolongo.price_comparison.repositories;

import com.mkolongo.price_comparison.domain.entities.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    Category findByName(String name);
}
