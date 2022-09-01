package com.dinsaren.bbuappserver.repository;

import com.dinsaren.bbuappserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsernameAndStatus(String username,  String status);
  Optional<User> findByPhoneAndStatus(String phone, String status);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
  Boolean existsByPhone(String phone);

}
