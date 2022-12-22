package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.model.entity.SellerEntity;
import com.example.twplatformaecommerce.repo.ProductRepo;
import com.example.twplatformaecommerce.repo.SellerRepo;
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
    private final SellerRepo sellerRepo;

    public List<ProductEntity> getProductsForWarehouse(String warehouseUsername){
        SellerEntity seller=sellerRepo.findSellerEntityByUsername(warehouseUsername).get();
        return productRepo.findAllByWarehouseName(seller.getName());
    }

    public List<ProductEntity> getProductsForStore(String storeUsername){
        SellerEntity seller=sellerRepo.findSellerEntityByUsername(storeUsername).get();
        return productRepo.findAllByWarehouseName(seller.getName());
    }

    public List<ProductEntity> getProductsFromAllWarehouses(){
        return productRepo.findAllByStoreNameIsNull();
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
