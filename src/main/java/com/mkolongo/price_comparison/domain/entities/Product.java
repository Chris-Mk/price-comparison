package com.mkolongo.price_comparison.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends NamedEntity {

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String description;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal price;

//    @Column(name = "image_url")
//    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "in_promo")
    private boolean inPromo;

    @Column(name = "in_stock")
    private boolean inStock;

}
