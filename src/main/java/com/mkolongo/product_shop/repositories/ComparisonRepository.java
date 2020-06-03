package com.mkolongo.product_shop.repositories;

import com.mkolongo.product_shop.domain.entities.Comparison;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComparisonRepository extends BaseRepository<Comparison> {

    Optional<Comparison> findByName(String name);

}
