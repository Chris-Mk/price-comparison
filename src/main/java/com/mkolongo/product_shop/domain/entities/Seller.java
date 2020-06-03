package com.mkolongo.product_shop.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller extends NamedEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "seller")
    private Set<Shop> shops;

//    @Column(name = "has_paid", nullable = false)
//    private boolean hasPaid;
}
