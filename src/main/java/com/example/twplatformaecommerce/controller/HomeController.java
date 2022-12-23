package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.*;
import com.example.twplatformaecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SecurityService securityService;
    private final FormService formService;
    private final SellerService sellerService;
    private final ProductService productService;
    private final StoreOrderService storeOrderService;
    private final UserOrderService userOrderService;
    private List<ProductEntity> products;
    private List<StoreOrderEntity> storeOrders;
    private List<UserOrderEntity> userOrders;
    private List<String> filters = new ArrayList<>();
    private String searchedTerm="";
    private Page<ProductEntity> productPage;

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
        } else if (granted.stream().filter(e -> e.contains("ROLE_SHOP")).findFirst().isPresent()) {
            products = productService.getProductsForStore(authentication.getPrincipal().toString());
            model.addAttribute("productList", products);
            return "shop_inventory";
        }
        userOrders = userOrderService.getAllOrdersByUsername(authentication.getPrincipal().toString());
        model.addAttribute("userOrderList", userOrders);
        return "user_inventory";
    }

    @GetMapping("/products/edit/{name}")
    public String editProduct(Model model, String error, String logout, @PathVariable("name") String name, final RedirectAttributes redirectAttributes) {
        ProductEntity product = products.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        redirectAttributes.addFlashAttribute("currentProduct", product);
        return "redirect:/newproduct";
    }

    @GetMapping("/buy")
    public String getBuyableProducts(Model model, String error, String logout, Authentication authentication) {
        products = productService.getProductsFromAllWarehouses();
        model.addAttribute("productList", products);
        return "warehouse_buy";
    }

    @GetMapping("/search")
    public String searchProducts(Model model, String error, String logout,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(1);

        productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), filters,searchedTerm);
        List<SellerEntity> stores = sellerService.getAllStores();

        model.addAttribute("searchTypes", stores);
        model.addAttribute("productPage", productPage);
        model.addAttribute("filters", filters);
        model.addAttribute("keyword",searchedTerm);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "user_search";
    }

    @GetMapping("/search/buy/{index}")
    public String buyProductUser(Model model, String error, String logout, @PathVariable("index") int index, final RedirectAttributes redirectAttributes) {
        ProductEntity product = productPage.getContent().get(index);
        redirectAttributes.addFlashAttribute("currentProduct", product);
        return "redirect:/userorder";
    }

    @PostMapping("/search")
    public String searchProducts(Model model, String error, String logout,
                                 @RequestParam(defaultValue = "") List<String> searchValues,
                                 @RequestParam(required = false) String keyword) {
        int currentPage = 1;
        int pageSize = 1;
        if (keyword == null)
            filters = searchValues;
        else
            searchedTerm=keyword;

        productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), filters,searchedTerm);
        List<SellerEntity> stores = sellerService.getAllStores();

        model.addAttribute("searchTypes", stores);
        model.addAttribute("productPage", productPage);
        model.addAttribute("filters", filters);
        model.addAttribute("keyword",searchedTerm);


        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "user_search";
    }

    @GetMapping("/buy/{index}")
    public String buyProductStore(Model model, String error, String logout, @PathVariable("index") int index, final RedirectAttributes redirectAttributes) {
        ProductEntity product = products.get(index);
        redirectAttributes.addFlashAttribute("currentProduct", product);
        return "redirect:/storeorder";
    }

    @GetMapping("/orders")
    public String getStoreOrders(Model model, String error, String logout, Authentication authentication, @ModelAttribute("errorMessage") String errorMessage) {
        storeOrders = storeOrderService.getOrdersByWarehouse(authentication.getPrincipal().toString());
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("orderList", storeOrders);
        return "warehouse_view_orders";
    }

    @GetMapping("/orders/approve/{index}")
    public String approveOrder(Model model, String error, String logout, @PathVariable("index") int index, final RedirectAttributes redirectAttributes) {
        StoreOrderEntity storeOrder = storeOrders.get(index);
        if (!productService.add(storeOrder))
            redirectAttributes.addFlashAttribute("errorMessage", "Not enough quantity for product");
        return "redirect:/orders";
    }

    @GetMapping("/orders/delete/{index}")
    public String deleteOrder(Model model, String error, String logout, @PathVariable("index") int index) {
        StoreOrderEntity storeOrder = storeOrders.get(index);
        storeOrderService.deleteOrder(storeOrder);
        return "redirect:/orders";
    }
}
