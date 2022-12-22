package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.model.entity.SellerEntity;
import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import com.example.twplatformaecommerce.repo.ProductRepo;
import com.example.twplatformaecommerce.repo.SellerRepo;
import com.example.twplatformaecommerce.repo.StoreOrderRepo;
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
    private final StoreOrderRepo storeOrderRepo;

    public List<ProductEntity> getProductsForWarehouse(String warehouseUsername) {
        SellerEntity seller = sellerRepo.findSellerEntityByUsername(warehouseUsername).get();
        return productRepo.findAllByWarehouseNameAndStoreNameIsNull(seller.getName());
    }

    public List<ProductEntity> getProductsForStore(String storeUsername) {
        SellerEntity seller = sellerRepo.findSellerEntityByUsername(storeUsername).get();
        return productRepo.findAllByStoreName(seller.getName());
    }

    public List<ProductEntity> getProductsFromAllWarehouses() {
        return productRepo.findAllByStoreNameIsNull();
    }

    public ProductEntity getProductByNameAndWarehouseName(String name, String warehouseName) {
        Optional<ProductEntity> optProduct = productRepo.findByNameAndWarehouseNameAndStoreName(name, warehouseName,null);
        if (optProduct.isPresent())
            return optProduct.get();
        return null;
    }

    public ProductEntity save(ProductEntity product) {
        return productRepo.save(product);
    }

    public boolean add(StoreOrderEntity storeOrder) {
        ProductEntity product = new ProductEntity();
        ProductEntity warehouseProduct = storeOrder.getProduct();
        Optional<ProductEntity> optProduct = productRepo.findByNameAndWarehouseNameAndStoreName(warehouseProduct.getName(), warehouseProduct.getWarehouseName(), storeOrder.getSeller().getName());
        if (optProduct.isPresent())
            product = optProduct.get();
        else {
            product = new ProductEntity();
            product.setQuantity("0");
        }
        int remainingQuantity = Integer.parseInt(warehouseProduct.getQuantity()) - Integer.parseInt(storeOrder.getWantedQuantity());
        if (remainingQuantity < 0)
            return false;
        product.setName(warehouseProduct.getName());
        product.setWarehouseName(warehouseProduct.getWarehouseName());

        product.setQuantity(Integer.toString(Integer.parseInt(product.getQuantity()) + Integer.parseInt(storeOrder.getWantedQuantity())));
        product.setStoreName(storeOrder.getSeller().getName());
        productRepo.save(product);


        warehouseProduct.setQuantity(Integer.toString(remainingQuantity));
        productRepo.save(warehouseProduct);

        storeOrderRepo.delete(storeOrder);
        return true;
    }
}
