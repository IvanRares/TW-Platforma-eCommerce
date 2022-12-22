package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class StoreOrderValidatorService implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return StoreOrderEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object storeOrderEntity, Errors errors) {
        StoreOrderEntity storeOrder = (StoreOrderEntity) storeOrderEntity;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wantedQuantity", "user.isUsernameEmpty");

        int wantedQuantity=Integer.parseInt(storeOrder.getWantedQuantity());
        int totalQuantity=Integer.parseInt(storeOrder.getProduct().getQuantity());
        if(wantedQuantity>totalQuantity)
            errors.rejectValue("wantedQuantity", "order.wantedQuantityTooLarge");

    }
}
