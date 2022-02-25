package com.example.application.controllers;

import com.example.application.models.TestUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isBlank(user.phoneNumber)) {
            model.addAttribute("phoneNumber", "unknown");
        } else {
            model.addAttribute("phoneNumber", user.phoneNumber);
        }
        return "greeting";
    }

}
