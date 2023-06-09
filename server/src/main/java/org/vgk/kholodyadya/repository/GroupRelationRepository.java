package org.vgk.kholodyadya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vgk.kholodyadya.entity.GroupRelation;

import java.util.List;
import java.util.Optional;

public interface GroupRelationRepository extends
        JpaRepository<GroupRelation, GroupRelation.GroupRelationId> {

    Optional<GroupRelation> findByRelationId(GroupRelation.GroupRelationId groupRelationId);

    List<GroupRelation> findByRelationId_UserId(int userId);
}