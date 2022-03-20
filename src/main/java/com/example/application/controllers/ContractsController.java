package com.example.application.controllers;

import com.example.application.dto.ContractDTO;
import com.example.application.services.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ContractsController {

    private final ContractService contractService;

    @GetMapping("contracts")
    public String index(Model model) {
        model.addAttribute("contracts", contractService.getContractsList());
        return "contracts";
    }

    @GetMapping("contracts/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("contract", contractService.getContract(id));
        return "contract";
    }

    @GetMapping("createContract")
    public String addContract(Model model) {
        model.addAttribute("contract", new ContractDTO());
        return "createContract";
    }

    @PostMapping("contracts")
    public String create(@ModelAttribute("contract") ContractDTO contract) {
        contractService.createContract(contract);
        return "success";
    }

    @DeleteMapping("contracts/{id}")
    public String delete(@PathVariable("id") int id) {
        contractService.deleteContract(id);
        return "success";
    }

    @GetMapping("contracts/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("contract", contractService.getContract(id));
        return "editContractByClient";
    }

    @GetMapping("contracts/{id}/editByManager")
    public String editByManager(Model model, @PathVariable("id") int id) {
        model.addAttribute("contract", contractService.getContract(id));
        return "editContractByManager";
    }

    @PostMapping("contracts/{id}")
    public String update(@ModelAttribute("contract") ContractDTO contract) {
        contractService.updateContract(contract);
        return "success";
    }
}
