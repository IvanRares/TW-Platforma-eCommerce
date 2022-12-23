package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findAllByWarehouseNameAndStoreNameIsNull(String warehouseName);
    List<ProductEntity> findAllByStoreName(String storeName);
    List<ProductEntity> findAllByStoreNameIsNull();
    List<ProductEntity> findAllByStoreNameIsNotNullAndNameContaining(String name, Pageable pageable);
    List<ProductEntity> findAllByStoreNameIsNotNull(Pageable pageable);
    List<ProductEntity> findAllByStoreNameIn(List<String> storeNames,Pageable pageable);
    List<ProductEntity> findAllByStoreNameInAndNameContaining(List<String> storeNames,String name,Pageable pageable);
    long countByStoreNameIsNotNull();
    long countByStoreNameIsNotNullAndNameContaining(String name);
    long countByStoreNameInAndNameContaining(List<String> storeNames,String name);
    long countByStoreNameIn(List<String> storeNames);
    Optional<ProductEntity> findProductEntityByNameAndWarehouseName(String name, String warehouseName);
    Optional<ProductEntity> findByNameAndWarehouseNameAndStoreName(String name,String warehouseName,String storeName);
}
