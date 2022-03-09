package com.example.application.services;

import com.example.application.dao.implementations.ClientDAO;
import com.example.application.models.ClientsEntity;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.application.dto.ClientDTO;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientsService {

    private final DozerBeanMapper mapper;

    private final ClientDAO clientDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<ClientDTO> getClientsList() {
        return clientDAO.findAll().stream()
                .map(entity -> mapper.map(entity, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public ClientDTO getClient(int id) {
        ClientsEntity client = clientDAO.show(id);
        return mapper.map(client, ClientDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteClient(int id) {
            clientDAO.delete(clientDAO.show(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createClient(ClientDTO client) {
        clientDAO.add(mapper.map(client, ClientsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateClient(ClientDTO client) {
        clientDAO.edit(mapper.map(client, ClientsEntity.class));
    }

}
