package com.example.application.services;

import com.example.application.dao.ClientDAO;
import com.example.application.models.ClientsEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.application.dto.ClientDTO;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientsService {

    private final DozerBeanMapper mapper;

    private final ClientDAO clientDAO;

    @Autowired
    public ClientsService(ClientDAO clientDAO, DozerBeanMapper mapper) {
        this.clientDAO = clientDAO;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClientDTO> getClientsList() {
        return clientDAO.index().stream()
                .map(entity -> mapper.map(entity, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ClientDTO getClient(int id) {
        ClientsEntity client = clientDAO.show(id);
        System.out.println(client.toString());
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
        ClientsEntity cl = mapper.map(client, ClientsEntity.class);
        clientDAO.edit(cl);
    }

}
