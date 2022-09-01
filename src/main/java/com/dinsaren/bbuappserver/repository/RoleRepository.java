package com.dinsaren.bbuappserver.repository;

import com.dinsaren.bbuappserver.models.Role;
import com.dinsaren.bbuappserver.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(UserRole name);
}
