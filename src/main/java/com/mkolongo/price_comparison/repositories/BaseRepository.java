package com.mkolongo.price_comparison.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E> extends JpaRepository<E, String> {

//    @Query("select e from #{#entityName} e where e.name = ?1")
//    Optional<E> findByName(String name);
}
