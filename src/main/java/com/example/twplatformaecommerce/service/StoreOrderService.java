package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.SellerEntity;
import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import com.example.twplatformaecommerce.repo.SellerRepo;
import com.example.twplatformaecommerce.repo.StoreOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreOrderService {
    private final StoreOrderRepo storeOrderRepo;
    private final SellerRepo sellerRepo;

    public List<StoreOrderEntity> getOrdersByWarehouse(String warehouseUsername){
        SellerEntity seller=sellerRepo.findSellerEntityByUsername(warehouseUsername).get();
        return storeOrderRepo.findAllByProduct_WarehouseName(seller.getName());
    }
    public void deleteOrder(StoreOrderEntity storeOrder){
        storeOrderRepo.delete(storeOrder);
    }
    public StoreOrderEntity save(StoreOrderEntity storeOrder){
        return storeOrderRepo.save(storeOrder);
    }
}
