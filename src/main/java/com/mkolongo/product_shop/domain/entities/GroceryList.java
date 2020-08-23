package com.mkolongo.product_shop.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "grocery_lists")
public class GroceryList extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "grocery_lists_products",
            joinColumns = @JoinColumn(name = "grocery_list_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<Product> products;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at", nullable = false)
    @PastOrPresent
    private LocalDateTime createdAt;

    @Column(name = "is_completed")
    private boolean isCompleted;

//    @Column(name = "total_price", precision = 10, scale = 2)
//    private BigDecimal totalPrice;
}
