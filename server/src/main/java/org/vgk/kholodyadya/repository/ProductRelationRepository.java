package org.vgk.kholodyadya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vgk.kholodyadya.entity.Product;
import org.vgk.kholodyadya.entity.ProductRelation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRelationRepository extends JpaRepository<ProductRelation, ProductRelation.ProductRelationId> {

    List<ProductRelation> findAllByRelationIdUserId(int userId);

    Optional<ProductRelation> findProductRelationByRelationId(ProductRelation.ProductRelationId relation);

    void deleteByRelationId(ProductRelation.ProductRelationId relation);
}