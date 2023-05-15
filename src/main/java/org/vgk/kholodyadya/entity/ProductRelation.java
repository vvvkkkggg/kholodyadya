package org.vgk.kholodyadya.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Embeddable
    public static class ProductRelationId implements Serializable {
        private int productId;
        private int userId;
    }
}