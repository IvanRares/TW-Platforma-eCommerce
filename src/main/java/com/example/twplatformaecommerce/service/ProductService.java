package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.model.entity.SellerEntity;
import com.example.twplatformaecommerce.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepo productRepo;

    public List<ProductEntity> getProductsForWarehouse(String warehouseUsername){
        return productRepo.findAllByWarehouseName(warehouseUsername);
    }

    public ProductEntity getProductByNameAndWarehouseName(String name,String warehouseName){
        Optional<ProductEntity> optProduct =productRepo.findProductEntityByNameAndWarehouseName(name,warehouseName);
        if(optProduct.isPresent())
            return optProduct.get();
        return null;
    }

    public ProductEntity save(ProductEntity product) {
        return productRepo.save(product);
    }
}
