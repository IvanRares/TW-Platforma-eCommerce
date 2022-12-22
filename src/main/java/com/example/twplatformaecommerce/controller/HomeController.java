package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.ProductEntity;
import com.example.twplatformaecommerce.model.entity.StoreOrderEntity;
import com.example.twplatformaecommerce.repo.StoreOrderRepo;
import com.example.twplatformaecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final StoreOrderService storeOrderService;
    private List<ProductEntity> products;
    private List<StoreOrderEntity> orders;
    private final StoreOrderRepo storeOrderRepo;

    @GetMapping()
    public String open(Model model, String error, String logout, @CookieValue(value = AUTHORIZATION, defaultValue = "") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer_")) {
            return "login";
        }
        return "index";
    }

    @GetMapping("/forms")
    public String getForms(Model model, String error, String logout) {
        List<FormEntity> forms = formService.getForms();
        model.addAttribute("formList", forms);
        return "forms";
    }

    @GetMapping("/forms/approve/{username}")
    public String addAccount(Model model, String error, String logout, @PathVariable("username") String username) {
        sellerService.add(username);
        return "redirect:/forms";
    }

    @GetMapping("/forms/delete/{username}")
    public String deleteAccount(Model model, String error, String logout, @PathVariable("username") String username) {
        formService.deleteForm(username);
        return "redirect:/forms";
    }

    @GetMapping("/products")
    public String getProducts(Model model, String error, String logout, Authentication authentication) {
        Collection<String> granted = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (granted.stream().filter(e -> e.contains("ROLE_WAREHOUSE")).findFirst().isPresent()) {
            products = productService.getProductsForWarehouse(authentication.getPrincipal().toString());
            model.addAttribute("productList", products);
            return "warehouse_inventory";
        }
        products = productService.getProductsForStore(authentication.getPrincipal().toString());
        model.addAttribute("productList", products);
        return "shop_inventory";
    }

    @GetMapping("/products/edit/{name}")
    public String editProduct(Model model, String error, String logout, @PathVariable("name") String name, final RedirectAttributes redirectAttributes) {
        ProductEntity product = products.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        redirectAttributes.addFlashAttribute("currentProduct", product);
        return "redirect:/newproduct";
    }

    @GetMapping("/buy")
    public String getBuyableProducts(Model model, String error, String logout) {
        products = productService.getProductsFromAllWarehouses();
        model.addAttribute("productList", products);
        return "warehouse_buy";
    }

    @GetMapping("/buy/{index}")
    public String buyProduct(Model model, String error, String logout, @PathVariable("index") int index, final RedirectAttributes redirectAttributes) {
        ProductEntity product = products.get(index);
        redirectAttributes.addFlashAttribute("currentProduct", product);
        return "redirect:/storeorder";
    }

    @GetMapping("/orders")
    public String getOrders(Model model, String error, String logout, Authentication authentication, @ModelAttribute("errorMessage") String errorMessage) {
        orders = storeOrderService.getOrdersByWarehouse(authentication.getPrincipal().toString());
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("orderList", orders);
        return "warehouse_view_orders";
    }

    @GetMapping("/orders/approve/{index}")
    public String approveOrder(Model model, String error, String logout, @PathVariable("index") int index, final RedirectAttributes redirectAttributes) {
        StoreOrderEntity storeOrder = orders.get(index);
        if (!productService.add(storeOrder))
            redirectAttributes.addFlashAttribute("errorMessage", "Not enough quantity for product");
        return "redirect:/orders";
    }

    @GetMapping("/orders/delete/{index}")
    public String deleteOrder(Model model, String error, String logout, @PathVariable("index") int index) {
        StoreOrderEntity storeOrder = orders.get(index);
        storeOrderService.deleteOrder(storeOrder);
        return "redirect:/orders";
    }
}
