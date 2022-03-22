package com.example.application.controllers.RestControllers;

import com.example.application.dto.ClientDTO;
import com.example.application.dto.ContractDTO;
import com.example.application.dto.OptionDTO;
import com.example.application.dto.PlanDTO;
import com.example.application.models.OptionsEntity;
import com.example.application.services.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ContractsRestController {

    private final ContractService contractService;

    @GetMapping("optionsByContract/{id}")
    public Set<OptionsEntity> getContractsOptions(@PathVariable("id") int id) {
        return contractService.getContract(id).getOptions();
    }

    @PostMapping("contracts/{id}/update")
    public ResponseEntity<String> update(@PathVariable("id") int id, @RequestParam Set<OptionDTO> options, @RequestParam PlanDTO plan) {
        contractService.updateContract(id, options, plan);
        return ResponseEntity.ok("Contract was updated");
    }

    @PostMapping("contracts/create")
    public ResponseEntity<String> createContract(@RequestParam Set<OptionDTO> options, @RequestParam PlanDTO plan, @RequestParam ClientDTO client, @RequestParam String phoneNumber) {
        contractService.createContract(options, plan, client, phoneNumber);
        return ResponseEntity.ok("Contract was created");
    }

    @PostMapping("contracts/{id}/block")
    public ResponseEntity<String> blockContract(@PathVariable("id") int id, @RequestParam boolean isBlocked, @RequestParam boolean isBlockedByManager) {
        contractService.blockContract(id, isBlocked, isBlockedByManager);
        return ResponseEntity.ok("Contract was updated");
    }

    @GetMapping("contracts/{id}/get")
    public ContractDTO getContractById(@PathVariable("id") int id) {
        return contractService.getContract(id);
    }

    @GetMapping("getPlan/{id}")
    public PlanDTO getContractsPlan(@PathVariable("id") int id) {
        return contractService.getContract(id).getPlan();
    }

    @GetMapping("allContracts")
    public List<ContractDTO> getAllContracts() {
        return contractService.getContractsList();
    }
}
