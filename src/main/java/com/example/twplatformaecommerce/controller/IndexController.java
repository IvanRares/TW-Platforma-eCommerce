package com.example.twplatformaecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexController {
    @GetMapping()
    public String open(Model model, String error, String logout, @CookieValue(value = AUTHORIZATION,defaultValue = "")String authorization){
        if (authorization==null||!authorization.startsWith("Bearer_")) {
            return "login";
        }
        return "index";
    }
}
