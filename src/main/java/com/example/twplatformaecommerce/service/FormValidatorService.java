package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
@RequiredArgsConstructor
public class FormValidatorService implements Validator {
    private final FormService formService;
    private final SellerService sellerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return FormEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object formEntity, Errors errors) {
        FormEntity user = (FormEntity) formEntity;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.isUsernameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.isPasswordEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "user.isUsernameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "user.isUsernameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "user.isUsernameEmpty");

        /* Valid email regex pattern - https://owasp.org/www-community/OWASP_Validation_Regex_Repository */
        /* Typical email format: email@domain.com */
        String emailRegexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        /*
           at least 8 digits {8,}
           at least one number (?=.*\d)
           at least one lowercase (?=.*[a-z])
           at least one uppercase (?=.*[A-Z])
           at least one special character (?=.*[@#$%^&+=])
           No space [^\s]
        */
        try {
            formService.getForm(user.getUsername());
            errors.rejectValue("username", "user.Exists");
        } catch (UsernameNotFoundException ex) {
        }
        try {
            formService.getFormByName(user.getName());
            errors.rejectValue("name", "user.Exists");
        } catch (UsernameNotFoundException ex) {
        }

        try {
            sellerService.getSeller(user.getUsername());
            errors.rejectValue("username", "user.Exists");
        } catch (UsernameNotFoundException ex) {
        }
        try {
            sellerService.getSellerByName(user.getName());
            errors.rejectValue("name", "user.Exists");
        } catch (UsernameNotFoundException ex) {
        }
        String passwordRegexPattern = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*)[^\s]{8,}$";
        Boolean isValidEmail = user.getUsername().matches(emailRegexPattern);
        Boolean isValidPassword = user.getPassword().matches(passwordRegexPattern);
        Boolean arePasswordTheSame = user.getPassword().equals(user.getPasswordConfirm());
        if (!isValidEmail)
            errors.rejectValue("username", "user.isValidEmail");
        if (!isValidPassword)
            errors.rejectValue("password", "user.isValidPassword");
        if (!arePasswordTheSame)
            errors.rejectValue("passwordConfirm", "user.isPasswordTheSame");

    }
}
