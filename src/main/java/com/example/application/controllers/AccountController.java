package com.example.application.controllers;

import com.example.application.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class    AccountController {
    private final ClientsService clientsService;

    @GetMapping("account/{id}")
    public String mainPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientsService.getClient(id));
        return "account";
    }
}
