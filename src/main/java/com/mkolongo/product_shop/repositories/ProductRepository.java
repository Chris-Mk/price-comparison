package com.mkolongo.product_shop.repositories;

import com.mkolongo.product_shop.domain.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends BaseRepository<Product> {

    @Query(value = "select * from product_shop_db.products p " +
                   "join product_shop_db.products_categories pc " +
                   "on p.id = pc.product_id " +
                   "join product_shop_db.categories c " +
                   "on c.id = pc.category_id " +
                   "where c.name = ?1", nativeQuery = true)
    Set<Product> findByCategory(String name);
}
