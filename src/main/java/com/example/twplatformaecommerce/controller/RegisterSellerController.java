package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import com.example.twplatformaecommerce.service.FormService;
import com.example.twplatformaecommerce.service.FormValidatorService;
import com.example.twplatformaecommerce.service.UserService;
import com.example.twplatformaecommerce.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller_register")
@RequiredArgsConstructor
public class RegisterSellerController {
    private final FormValidatorService formValidatorService;
    private final FormService formService;

    @GetMapping()
    public String open(Model model){
        System.out.println(model);
        model.addAttribute("userForm", new FormEntity());

        return "seller_register";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") FormEntity userForm, BindingResult bindingResult){
        formValidatorService.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
            return "seller_register";

        formService.save(userForm);
        return "form_success_register";
    }
}
