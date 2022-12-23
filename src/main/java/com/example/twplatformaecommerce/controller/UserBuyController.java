package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.*;
import com.example.twplatformaecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userorder")
@RequiredArgsConstructor
public class UserBuyController {
    private final UserService userService;
    private final UserOrderService userOrderService;
    private final UserOrderValidatorService userOrderValidatorService;
    private UserEntity user;
    private ProductEntity currentProduct;
    private UserOrderEntity userForm;

    @GetMapping()
    public String open(Model model, String error, String logout, @ModelAttribute("currentProduct") ProductEntity product,BindingResult bindingResult) {
        if (product.getName() == null)
            return "redirect:/products";
        userForm = new UserOrderEntity();
        userForm.setProduct(product);
        model.addAttribute("userForm", userForm);
        currentProduct = product;
        return "user_order_form";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") UserOrderEntity userForm, BindingResult bindingResult, Authentication authentication) {
        user = userService.getUser(authentication.getPrincipal().toString());
        userForm.setUser(user);
        userForm.setProduct(currentProduct);
        userOrderValidatorService.validate(userForm, bindingResult);
        if (bindingResult.hasErrors())
            return "user_order_form";
        userOrderService.save(userForm);
        return "redirect:/products";
    }
}
