package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepo extends JpaRepository<SellerEntity,Long> {
    Optional<SellerEntity> findSellerEntityByUsername(String username);
}
