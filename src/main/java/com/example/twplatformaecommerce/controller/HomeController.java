package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SecurityService securityService;

    @GetMapping()
    public String open(Model model, String error, String logout, @CookieValue(value = AUTHORIZATION,defaultValue = "")String authorization){
        if (authorization==null||!authorization.startsWith("Bearer_")) {
            return "login";
        }
        return "index";
    }
}
