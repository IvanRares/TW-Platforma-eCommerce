package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.model.entity.UserOrderEntity;
import com.example.twplatformaecommerce.repo.ProductRepo;
import com.example.twplatformaecommerce.repo.UserOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserOrderService {
    private final UserOrderRepo userOrderRepo;
    private final ProductRepo productRepo;

    public List<UserOrderEntity> getAllOrdersByUsername(String username){
        return userOrderRepo.findAllByUser_Username(username);
    }

    public UserOrderEntity save(UserOrderEntity userOrderEntity){
        ProductEntity product=userOrderEntity.getProduct();
        product.setQuantity(Integer.toString(Integer.parseInt(product.getQuantity())-Integer.parseInt(userOrderEntity.getWantedQuantity())));
        productRepo.save(product);
        return userOrderRepo.save(userOrderEntity);
    }
}
