package org.vgk.kholodyadya.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_relations")
public class ProductRelation {
    @EmbeddedId
    private ProductRelationId relationId;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Embeddable
    public static class ProductRelationId implements Serializable {
        private int productId;
        private int userId;
    }
}