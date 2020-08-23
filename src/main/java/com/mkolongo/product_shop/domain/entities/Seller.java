package com.mkolongo.product_shop.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sellers")
@DiscriminatorValue(value = "seller")
public class Seller extends Person {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "seller")
    private Set<Shop> shops;

//    @Column(name = "has_paid", nullable = false)
//    private boolean hasPaid;
}
