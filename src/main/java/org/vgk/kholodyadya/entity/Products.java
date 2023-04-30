package org.vgk.kholodyadya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@org.hibernate.annotations.NamedQuery(
        name = "DeptEmployee_FindAllByDepartment",
        query = "from Products where productName = :product_name",
        timeout = 1,
        fetchSize = 10
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Products {

    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int productId;

    @Column(name="product_name")
    private String productName;

//    @ManyToMany(mappedBy = "products")
//    private Set<Users> users = new HashSet<>();
}
