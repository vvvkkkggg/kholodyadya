package org.vgk.kholodyadya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    @Column(name="first_name")
    private String firstName;

//    @ManyToMany(cascade = {
//            CascadeType.MERGE
//    })
//    @JoinTable(
//            name = "products_relations",
//            joinColumns = @JoinColumn(name="user_id"),
//            inverseJoinColumns = @JoinColumn(name="product_id"))
//    private Set<Products> products = new HashSet<>();
}
