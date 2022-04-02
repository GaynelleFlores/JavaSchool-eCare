package com.example.application.controllers.RestControllers;

import com.example.application.dto.ClientDTO;
import com.example.application.dto.ContractDTO;
import com.example.application.exceptions.BusinessLogicException;
import com.example.application.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientsRestController {

    private final BCryptPasswordEncoder encoder;

    private final ClientsService clientsService;

    @PostMapping("clients/{id}/createAccount")
    public ResponseEntity<String> createAccount(@PathVariable("id") int id, @RequestParam String login, @RequestParam String password) {
        ClientDTO client = clientsService.getClient(id);
        if (client.getLogin() != null) {
            throw new BusinessLogicException("Account already exists.");
        }
        client.setLogin(login);
        client.setPassword(encoder.encode(password).toCharArray());

        clientsService.updateClient(client);
        clientsService.setRole(client, 1);
        return ResponseEntity.ok("Client was updated");
    }

    @GetMapping("allClients")
    public List<ClientDTO> getAllClients() {
        return  clientsService.getClientsList();
    }

    @GetMapping("clients/{id}/getContracts")
    public List<ContractDTO> getContractsByClient(@PathVariable("id") int id) {
        return clientsService.getClient(id).getContracts();
    }
}
