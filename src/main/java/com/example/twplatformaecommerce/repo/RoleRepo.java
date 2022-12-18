package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByName(String name);
}
