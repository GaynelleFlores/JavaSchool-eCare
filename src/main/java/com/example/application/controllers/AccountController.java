package com.example.application.controllers;

import com.example.application.UserDetailsImpl;
import com.example.application.models.Role;
import com.example.application.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final ClientsService clientsService;

    @GetMapping("account/{id}")
    public String mainPage(@PathVariable("id") int id, Model model) {
        UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role admin = new Role();
        admin.setId(2);
        admin.setName("ROLE_ADMIN");
        if (currentUser.getClient().getId() != id && !currentUser.getClient().getRoles().contains(admin)) {
            throw new AccessDeniedException("Access denied");
        }
        model.addAttribute("client", clientsService.getClient(id));
        return "account";
    }
}
