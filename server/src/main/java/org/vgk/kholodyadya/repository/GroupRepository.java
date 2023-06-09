package org.vgk.kholodyadya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vgk.kholodyadya.entity.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}