package com.mkolongo.price_comparison.repositories;

import com.mkolongo.price_comparison.domain.entities.Comparison;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ComparisonRepository extends BaseRepository<Comparison> {

    Optional<Comparison> findByName(String name);

    Set<Comparison> findByUser_Email(String userEmail);

    void deleteByName(String name);
}
