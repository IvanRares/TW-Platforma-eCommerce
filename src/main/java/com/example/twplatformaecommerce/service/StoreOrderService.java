package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import com.example.twplatformaecommerce.repo.StoreOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreOrderService {
    private final StoreOrderRepo storeOrderRepo;

    public StoreOrderEntity save(StoreOrderEntity storeOrder){
        return storeOrderRepo.save(storeOrder);
    }
}
