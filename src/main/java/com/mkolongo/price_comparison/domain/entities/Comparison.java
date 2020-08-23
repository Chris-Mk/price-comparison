package com.mkolongo.price_comparison.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "comparisons")
public class Comparison extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(name = "comparison_lists_products",
            joinColumns = @JoinColumn(name = "comparison_list_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<Product> products;
}
