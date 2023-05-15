package org.vgk.kholodyadya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vgk.kholodyadya.entity.ProductRelation;

@Repository
public interface ProductRelationRepository extends JpaRepository<ProductRelation, ProductRelation.ProductRelationId> {
}