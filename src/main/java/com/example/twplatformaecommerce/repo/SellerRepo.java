package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.RoleEntity;
import com.example.twplatformaecommerce.model.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SellerRepo extends JpaRepository<SellerEntity, Long> {
    Optional<SellerEntity> findSellerEntityByUsername(String username);

    Optional<SellerEntity> findByName(String name);

    List<SellerEntity> findAllByRoles_(RoleEntity role);
}
