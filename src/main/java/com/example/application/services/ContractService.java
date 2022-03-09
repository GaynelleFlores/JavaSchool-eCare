package com.example.application.services;

import com.example.application.dao.implementations.ContractDAO;
import com.example.application.dto.ContractDTO;
import com.example.application.models.ClientsEntity;
import com.example.application.models.ContractsEntity;
import com.example.application.models.PlansEntity;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final DozerBeanMapper mapper;

    private final ContractDAO contractDAO;

    private final ClientsService clientsService;

    private final PlansService plansService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ContractDTO> getContractsList() {
        return contractDAO.findAll().stream()
                .map(entity -> mapper.map(entity, ContractDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ContractDTO getContract(int id) {
        ContractsEntity contract = contractDAO.show(id);
        return mapper.map(contract, ContractDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createContract(ContractDTO contract) {
        ClientsEntity client = mapper.map(clientsService.getClient(contract.getClient().getId()), ClientsEntity.class);
        PlansEntity plan = mapper.map(plansService.getPlan(contract.getPlan().getId()), PlansEntity.class);
        contract.setClient(client);
        contract.setPlan(plan);
        contractDAO.add(mapper.map(contract, ContractsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteContract(int id) {
        ContractsEntity contract = contractDAO.show(id);
        contractDAO.delete(contract);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateContract(ContractDTO contract) {
        ClientsEntity clients = mapper.map(clientsService.getClient(contract.getClient().getId()), ClientsEntity.class);
        PlansEntity plan = mapper.map(plansService.getPlan(contract.getPlan().getId()), PlansEntity.class);
        contract.setClient(clients);
        contract.setPlan(plan);
        contractDAO.edit(mapper.map(contract, ContractsEntity.class));
    }

}
