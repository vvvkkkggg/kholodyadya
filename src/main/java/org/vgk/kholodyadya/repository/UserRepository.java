package org.vgk.kholodyadya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vgk.kholodyadya.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
