package com.mkolongo.price_comparison.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

}
