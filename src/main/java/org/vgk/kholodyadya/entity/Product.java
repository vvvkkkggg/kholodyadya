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
@Table(name = "products")
public class Product {

    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int productId;

    @Column(name="product_name")
    private String productName;

    @ManyToMany(mappedBy = "products")
    private Set<User> users = new HashSet<>();
}
