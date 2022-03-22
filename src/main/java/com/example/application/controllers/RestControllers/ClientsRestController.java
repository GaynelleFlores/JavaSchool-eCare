package com.example.application.controllers.RestControllers;

import com.example.application.dto.ClientDTO;
import com.example.application.dto.ContractDTO;
import com.example.application.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientsRestController {

    private final ClientsService clientsService;

    @GetMapping("allClients")
    public List<ClientDTO> getAllClients() {
        return  clientsService.getClientsList();
    }

    @GetMapping("clients/{id}/getContracts")
    public List<ContractDTO> getContractsByClient(@PathVariable("id") int id) {
        return clientsService.getClient(id).getContracts();
    }
}
