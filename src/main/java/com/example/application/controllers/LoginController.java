package com.example.application.controllers;

import com.example.application.models.TestUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.Objects;

@Controller
public class LoginController {
    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("user", new TestUser());
        return "signin";
    }

    @PostMapping("/signin")
    public String greeting(TestUser user, Model model) {
        if (!Objects.equals(user.phoneNumber, "")) {
            model.addAttribute("phoneNumber", user.phoneNumber);
        } else {
            model.addAttribute("phoneNumber", "unknown");
        }
        return "greeting";
    }
}
