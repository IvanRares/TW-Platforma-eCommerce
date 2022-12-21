package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.service.ProductService;
import com.example.twplatformaecommerce.service.ProductValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/newproduct")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductValidatorService productValidatorService;
    private boolean isEditing = false;
    private ProductEntity currentProduct;

    @GetMapping()
    public String open(Model model, String error, String logout, @ModelAttribute("currentProduct") ProductEntity product) {
        if (product.getName() == null)
            model.addAttribute("productForm", new ProductEntity());
        else {
            model.addAttribute("productForm", product);
            currentProduct = product;
            isEditing = true;
        }
        return "product_form";
    }

    @PostMapping()
    public String register(@ModelAttribute("productForm") ProductEntity productForm, BindingResult bindingResult, Authentication authentication) {
        productForm.setWarehouseName(authentication.getPrincipal().toString());
        productValidatorService.validate(productForm, bindingResult, isEditing);
        if (bindingResult.hasErrors())
            return "product_form";
        if (currentProduct != null)
            productForm.setId(currentProduct.getId());
        productService.save(productForm);
        return "redirect:/products";
    }
}
