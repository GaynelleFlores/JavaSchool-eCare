package com.example.application.services;

import com.example.application.dao.implementations.ClientDAO;
import com.example.application.exceptions.BusinessLogicException;
import com.example.application.models.ClientsEntity;
import com.example.application.validation.ClientValidation;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.application.dto.ClientDTO;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientsService {

    private final ClientValidation clientValidation;

    private final DozerBeanMapper mapper;

    private final ClientDAO clientDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public ClientDTO getClientByLogin(String login) {
        ClientsEntity client = clientDAO.getClientByLogin(login);
        if (client == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return mapper.map(client, ClientDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<ClientDTO> getClientsList() {
        return clientDAO.findAll().stream()
                .map(entity -> mapper.map(entity, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public ClientDTO getClient(int id) {
        ClientsEntity client = clientDAO.show(id);
        if (client == null) {
            throw new BusinessLogicException("Client not found");
        }
        return mapper.map(client, ClientDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteClient(int id) {
            clientDAO.delete(clientDAO.show(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createClient(ClientDTO client) {
        if (client.getEmail().isEmpty()) {
            client.setEmail(null);
        }
        if (!clientValidation.validateClient(mapper.map(client, ClientsEntity.class))) {
            throw new BusinessLogicException("Failed to create client. Some field contains invalid information.");
        }
        clientDAO.add(mapper.map(client, ClientsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setRole(ClientDTO client, int roleId) {
        clientDAO.addRole(mapper.map(clientDAO.show(client.getId()), ClientsEntity.class), roleId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateClient(ClientDTO client) {
        if (!clientValidation.validateClient(mapper.map(client, ClientsEntity.class))) {
            throw new BusinessLogicException("Failed to update client. Some field contains invalid information.");
        }
        clientDAO.edit(mapper.map(client, ClientsEntity.class));
    }
}
