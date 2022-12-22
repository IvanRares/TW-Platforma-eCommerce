package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductValidatorService implements Validator {
    private final ProductService productService;
    private final ProductRepo productRepo;

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object productEntity, Errors errors) {
        ProductEntity product = (ProductEntity) productEntity;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "product.isNameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "product.isQuantityEmpty");
    }

    public void validate(Object productEntity, Errors errors,boolean isEditing) {
        ProductEntity product = (ProductEntity) productEntity;
        ProductEntity productExists=productService.getProductByNameAndWarehouseName(product.getName(),product.getWarehouseName());
        if(!isEditing&&productExists!=null)
            errors.rejectValue("name","product.Exists");
        validate(productEntity,errors);
    }
}
