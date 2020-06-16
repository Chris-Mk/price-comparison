package com.mkolongo.product_shop.repositories;

import com.mkolongo.product_shop.domain.entities.Seller;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends BaseRepository<Seller> {

    Optional<Seller> findByName(String name);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

}
