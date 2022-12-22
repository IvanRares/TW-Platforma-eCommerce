package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.model.entity.SellerEntity;
import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import com.example.twplatformaecommerce.service.ProductService;
import com.example.twplatformaecommerce.service.SellerService;
import com.example.twplatformaecommerce.service.StoreOrderService;
import com.example.twplatformaecommerce.service.StoreOrderValidatorService;
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
@RequestMapping("/storeorder")
@RequiredArgsConstructor
public class StoreBuyController {
    private final ProductService productService;
    private final StoreOrderService storeOrderService;
    private final StoreOrderValidatorService storeOrderValidatorService;
    private final SellerService sellerService;
    private ProductEntity currentProduct;
    private SellerEntity seller;
    private StoreOrderEntity storeForm;
    @GetMapping()
    public String open(Model model, String error, String logout, @ModelAttribute("currentProduct") ProductEntity product) {
        if (product.getName() == null)
            return "redirect:/products";
        storeForm=new StoreOrderEntity();
        storeForm.setProduct(product);
        model.addAttribute("storeForm", storeForm);
        currentProduct = product;
        return "store_order_form";
    }

    @PostMapping()
    public String register(@ModelAttribute("storeForm") StoreOrderEntity storeForm, BindingResult bindingResult, Authentication authentication) {
        seller=sellerService.getSeller(authentication.getPrincipal().toString());
        storeForm.setSeller(seller);
        storeForm.setProduct(currentProduct);
        storeOrderValidatorService.validate(storeForm, bindingResult);
        if (bindingResult.hasErrors())
            return "store_order_form";
        storeOrderService.save(storeForm);
        return "redirect:/buy";
    }
}
