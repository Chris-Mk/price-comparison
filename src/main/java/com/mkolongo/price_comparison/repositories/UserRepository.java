package com.mkolongo.price_comparison.repositories;

import com.mkolongo.price_comparison.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
