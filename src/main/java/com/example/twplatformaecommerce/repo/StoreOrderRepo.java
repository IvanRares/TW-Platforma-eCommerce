package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.SellerEntity;
import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreOrderRepo  extends JpaRepository<StoreOrderEntity,Long> {
    public List<StoreOrderEntity> findAllByProduct_WarehouseName(String warehouseName);
}
