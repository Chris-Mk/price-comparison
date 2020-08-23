package com.mkolongo.product_shop.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@DiscriminatorValue(value = "user")
public class User extends Person {

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "user")
    private Set<GroceryList> groceryLists;

    @OneToMany(mappedBy = "user")
    private Set<Comparison> comparisons;

//    @Column(name = "profile_pic_url")
//    private String profilePicUrl;

//    @OneToMany
//    @JoinColumn(name = "friend_id", referencedColumnName = "id")
//    private Set<User> friends;

}
