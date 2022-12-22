package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormRepo extends JpaRepository<FormEntity,Long> {
    Optional<FormEntity> findByUsername(String username);
    Optional<FormEntity> findByName(String name);
    List<FormEntity> findAll();
    long deleteByUsername(String username);
}
