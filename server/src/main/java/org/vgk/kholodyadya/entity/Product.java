package org.vgk.kholodyadya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.vgk.kholodyadya.contoller.Controller;

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

    private String category;
    private String image;

    public Product(Controller.ProductRequest productRequest) {
        this.productName = productRequest.getProductName();
        this.category = productRequest.getCategory();
    }
}
