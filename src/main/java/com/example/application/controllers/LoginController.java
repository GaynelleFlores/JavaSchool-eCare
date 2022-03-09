package com.example.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @PostMapping("/signin")
    public String greeting() {
        return "greeting";
    }
}
