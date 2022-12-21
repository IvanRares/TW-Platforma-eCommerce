package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import com.example.twplatformaecommerce.service.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SecurityService securityService;
    private final FormService formService;
    private final SellerService sellerService;
    private final ProductService productService;
    private List<ProductEntity> products;

    @GetMapping()
    public String open(Model model, String error, String logout, @CookieValue(value = AUTHORIZATION,defaultValue = "")String authorization){
        if (authorization==null||!authorization.startsWith("Bearer_")) {
            return "login";
        }
        return "index";
    }

    @GetMapping("/forms")
    public String getForms(Model model,String error, String logout){
        List<FormEntity> forms=formService.getForms();
        model.addAttribute("formList",forms);
        return "forms";
    }

    @GetMapping("/forms/approve/{username}")
    public String addAccount(Model model,String error,String logout, @PathVariable("username") String username){
        sellerService.add(username);
        return "redirect:/forms";
    }

    @GetMapping("/forms/delete/{username}")
    public String deleteAccount(Model model, String error, String logout, @PathVariable("username") String username){
        formService.deleteForm(username);
        return "redirect:/forms";
    }

    @GetMapping("/products")
    public String getProducts(Model model, String error, String logout, Authentication authentication){
        Collection<String> granted=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if(granted.stream().filter(e->e.contains("ROLE_WAREHOUSE")).findFirst().isPresent()){
            products=productService.getProductsForWarehouse(authentication.getPrincipal().toString());
            model.addAttribute("productList",products);
            return "warehouse_inventory";
        }
        return "warehouse_inventory";
    }

    @GetMapping("/products/edit/{name}")
    public String open(Model model, String error, String logout, @PathVariable("name") String name, final RedirectAttributes redirectAttributes){
        ProductEntity product=products.stream().filter(x->x.getName().equals(name)).findFirst().orElse(null);
        redirectAttributes.addFlashAttribute("currentProduct",product);
        return "redirect:/newproduct";
    }
}
