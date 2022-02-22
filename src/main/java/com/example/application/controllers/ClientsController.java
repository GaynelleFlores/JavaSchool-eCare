package com.example.application.controllers;

import com.example.application.dao.ClientDAO;
import com.example.application.models.ClientsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientsController {

    private final ClientDAO clientDAO;

    @Autowired
    public ClientsController(){
        this.clientDAO = new ClientDAO();
    }

    @GetMapping("clients")
    public String index(Model model) {
        try {
            model.addAttribute("clients", clientDAO.index());
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "clients";
    }

    @GetMapping("clients/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        ClientsEntity client = null;
        try {
            client = clientDAO.show(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        model.addAttribute("client", client);
        return "client";
    }

    @GetMapping("/createClient")
    public String addClient(Model model) {
        ClientsEntity client = new ClientsEntity();
        model.addAttribute("client", client);
        return "createClient";
    }

    @PostMapping("/clients")
    public String create(@ModelAttribute("client") ClientsEntity client) {
        try {
            clientDAO.add(client);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @DeleteMapping("clients/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        try {
            clientDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @GetMapping("clients/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        ClientsEntity client = null;
        try {
            client = clientDAO.show(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        model.addAttribute("client", client);
        return "editClient";
    }

    @PatchMapping("clients/{id}")
    public String update(@ModelAttribute("client") ClientsEntity client, @PathVariable("id") int id) {
        try {
            clientDAO.edit(client);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }
}
