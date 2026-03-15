package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // Trả về file login.html trong thư mục templates
        return "login"; 
    }
    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Trả về file 403.html
    }
}