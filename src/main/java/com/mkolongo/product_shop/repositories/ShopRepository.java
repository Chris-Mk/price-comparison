package com.mkolongo.product_shop.repositories;

import com.mkolongo.product_shop.domain.entities.Shop;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends BaseRepository<Shop> {

    Optional<Shop> findByName(String name);
}
