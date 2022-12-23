package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import com.example.twplatformaecommerce.model.entity.UserOrderEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class UserOrderValidatorService implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserOrderEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object userOrderEntity, Errors errors) {
        UserOrderEntity userOrder = (UserOrderEntity) userOrderEntity;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wantedQuantity", "user.isUsernameEmpty");

        int wantedQuantity=Integer.parseInt(userOrder.getWantedQuantity());
        int totalQuantity=Integer.parseInt(userOrder.getProduct().getQuantity());
        if(wantedQuantity>totalQuantity)
            errors.rejectValue("wantedQuantity", "order.wantedQuantityTooLarge");

    }
}
