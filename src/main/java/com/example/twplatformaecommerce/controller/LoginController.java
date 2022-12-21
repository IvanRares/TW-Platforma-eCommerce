package com.example.twplatformaecommerce.controller;

import com.example.twplatformaecommerce.security.SecurityConfig;
import com.example.twplatformaecommerce.service.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final SecurityService securityService;
    @GetMapping()
    public String open(Model model, String error, String logout, @CookieValue(value = AUTHORIZATION,defaultValue = "")String authorization){
//        if (authorization!=null&&authorization.startsWith("Bearer_"))  {
//            return "redirect:/";
//        }

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }


    @GetMapping("/error")
    public String error(Model model, String error, String logout){
        return "login";
    }

}
