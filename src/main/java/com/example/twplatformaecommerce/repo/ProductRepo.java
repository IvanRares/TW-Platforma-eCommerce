package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findAllByWarehouseName(String warehouseName);
    Optional<ProductEntity> findProductEntityByNameAndWarehouseName(String name, String warehouseName);
}
