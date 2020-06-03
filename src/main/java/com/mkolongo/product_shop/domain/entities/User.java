package com.mkolongo.product_shop.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<GroceryList> groceryLists;

    @OneToMany(mappedBy = "user")
    private Set<Comparison> comparisons;

//    @Column(name = "profile_pic_url")
//    private String profilePicUrl;

//    @OneToMany
//    @JoinColumn(name = "friend_id", referencedColumnName = "id")
//    private Set<User> friends;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

}
