package org.vgk.kholodyadya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vgk.kholodyadya.entity.Product;
import org.vgk.kholodyadya.entity.ProductRelation;

import java.util.List;

@Repository
public interface ProductRelationRepository extends JpaRepository<ProductRelation, ProductRelation.ProductRelationId> {

    List<ProductRelation> findAllByRelationIdUserId(int userId);
}