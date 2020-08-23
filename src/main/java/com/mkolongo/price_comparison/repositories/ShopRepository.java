package com.mkolongo.price_comparison.repositories;

import com.mkolongo.price_comparison.domain.entities.Shop;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends BaseRepository<Shop> {

    Optional<Shop> findByName(String name);
}
