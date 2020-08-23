package com.mkolongo.price_comparison.repositories;

import com.mkolongo.price_comparison.domain.entities.Seller;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends BaseRepository<Seller> {

    Optional<Seller> findByName(String name);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

}
