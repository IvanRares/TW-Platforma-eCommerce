package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.UserEntity;
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
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserValidatorService userValidatorService;
    private final UserService userService;

    @GetMapping()
    public String open(Model model){
        System.out.println(model);
        model.addAttribute("userForm", new UserEntity());

        return "register";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult){
        userValidatorService.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
            return "register";

        userService.save(userForm);
        userService.addRoleToUser(userForm.getUsername(),"ROLE_USER");
        return "login";
    }
}
