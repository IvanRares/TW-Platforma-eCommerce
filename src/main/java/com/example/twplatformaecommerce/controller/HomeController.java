package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.service.FormService;
import com.example.twplatformaecommerce.service.FormValidatorService;
import com.example.twplatformaecommerce.service.SecurityService;
import com.example.twplatformaecommerce.service.SellerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SecurityService securityService;
    private final FormService formService;
    private final SellerService sellerService;

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
}
