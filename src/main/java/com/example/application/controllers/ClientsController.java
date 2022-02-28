package com.example.application.controllers;

import com.example.application.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.application.dto.ClientDTO;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService){
        this.clientsService = clientsService;
    }

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

    @GetMapping("/createClient")
    public String addClient(Model model) {
        model.addAttribute("client", new ClientDTO());
        return "createClient";
    }

    @PostMapping("/clients")
    public String create(@ModelAttribute("client") ClientDTO client) {
        clientsService.createClient(client);
        return "success";
    }

    @DeleteMapping("clients/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        clientsService.deleteClient(id);
        return "success";
    }

    @GetMapping("clients/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("client", clientsService.getClient(id));
        return "editClient";
    }

    @PatchMapping("clients/{id}")
    public String update(@ModelAttribute("client") ClientDTO client, @PathVariable("id") int id) {

        clientsService.updateClient(client);
        return "success";
    }
}
