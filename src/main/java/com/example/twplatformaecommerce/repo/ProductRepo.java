package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findAllByWarehouseNameAndStoreNameIsNull(String warehouseName);
    List<ProductEntity> findAllByStoreName(String storeName);
    List<ProductEntity> findAllByStoreNameIsNull();
    Optional<ProductEntity> findProductEntityByNameAndWarehouseName(String name, String warehouseName);
    Optional<ProductEntity> findByNameAndWarehouseNameAndStoreName(String name,String warehouseName,String storeName);
}
