package com.dinsaren.bbuappserver.repository;

import com.dinsaren.bbuappserver.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByStatus(String status);
    Optional<Category> findByIdAndStatus(Integer id, String status);
}
