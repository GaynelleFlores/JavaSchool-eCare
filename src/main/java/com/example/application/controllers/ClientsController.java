package com.example.application.controllers;

import com.example.application.services.ClientsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.application.dto.ClientDTO;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ClientsController {

    private final ClientsService clientsService;

    @GetMapping("clients")
    public String index(Model model) {
        model.addAttribute("clients", clientsService.getClientsList());
        return "clients";
    }

    @GetMapping("clients/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientsService.getClient(id));
        return "client";
    }

    @GetMapping("createClient")
    public String addClient(Model model) {
        model.addAttribute("client", new ClientDTO());
        return "createClient";
    }

    @PostMapping("clients")
    public String create(@ModelAttribute("client") ClientDTO client) {
        clientsService.createClient(client);
        return "success";
    }

    @DeleteMapping("clients/{id}")
    public String delete(@PathVariable("id") int id) {
        clientsService.deleteClient(id);
        return "success";
    }

    @GetMapping("clients/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("client", clientsService.getClient(id));
        return "editClient";
    }

    @PostMapping("clients/{id}")
    public String update(@ModelAttribute("client") ClientDTO client) {
        clientsService.updateClient(client);
        return "success";
    }
}
