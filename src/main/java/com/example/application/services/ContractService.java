package com.example.application.services;

import com.example.application.dao.implementations.ContractDAO;
import com.example.application.dto.ClientDTO;
import com.example.application.dto.ContractDTO;
import com.example.application.dto.OptionDTO;
import com.example.application.dto.PlanDTO;
import com.example.application.exceptions.BusinessLogicException;
import com.example.application.mapping.SetMapping;
import com.example.application.models.ClientsEntity;
import com.example.application.models.ContractsEntity;
import com.example.application.models.PlansEntity;
import com.example.application.validation.ContractValidation;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final DozerBeanMapper mapper;

    private final ContractDAO contractDAO;

    private final ClientsService clientsService;

    private final PlansService plansService;

    private final ContractValidation contractValidation;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<ContractDTO> getContractsList() {
        return contractDAO.findAll().stream()
                .map(entity -> mapper.map(entity, ContractDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public ContractDTO getContract(int id) {
        ContractsEntity contract = contractDAO.show(id);
        if (contract == null) {
            throw new BusinessLogicException("Contract not found");
        }
        return mapper.map(contract, ContractDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public ContractDTO getContractByPhoneNumber(String phone) {
        ContractsEntity contract = contractDAO.getContractByPhoneNumber(phone);
        if (contract == null) {
            throw new BusinessLogicException("Contract not found");
        }
        return mapper.map(contract, ContractDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createContract(ContractDTO contract) {
        setFields(contract);
        contractDAO.add(mapper.map(contract, ContractsEntity.class));
    }

    private void setFields(ContractDTO contract) {
        ClientDTO client = clientsService.getClient(contract.getClient().getId());
        PlansEntity plan = mapper.map(plansService.getPlan(contract.getPlan().getId()), PlansEntity.class);
        contract.setClient(client);
        contract.setPlan(mapper.map(plan, PlanDTO.class));
        if (!contractValidation.validateContract(mapper.map(contract, ContractsEntity.class))) {
            throw new BusinessLogicException("Failed to update contract, contract is invalid.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createContract(Set<OptionDTO> options, PlanDTO plan, ClientDTO client, String phoneNumber) {
        ContractsEntity contract = new ContractsEntity();
        contract.setOptions(SetMapping.optionsMapping(options));
        contract.setPhoneNumber(phoneNumber);
        contract.setPlan(mapper.map(plansService.getPlan(plan.getId()), PlansEntity.class));
        contract.setClient(mapper.map(client, ClientsEntity.class));
        if (!contractValidation.validateContract(mapper.map(contract, ContractsEntity.class))) {
            throw new BusinessLogicException("Failed to create contract, contract is invalid.");
        }
        contractDAO.add(mapper.map(contract, ContractsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteContract(int id) {
        ContractsEntity contract = contractDAO.show(id);
        contractDAO.delete(contract);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateContract(ContractDTO contract) {
        setFields(contract);
        contractDAO.edit(mapper.map(contract, ContractsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateContract(int id, Set<OptionDTO> options, PlanDTO plan) {
        ContractsEntity contract = contractDAO.show(id);
        contract.setOptions(SetMapping.optionsMapping(options));
        contract.setPlan(mapper.map(plansService.getPlan(plan.getId()), PlansEntity.class));
        if (!contractValidation.validateContract(contract)) {
            throw new BusinessLogicException("Failed to update contract, contract is invalid.");
        }
        contractDAO.edit(contract);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void blockContract(int id, boolean isBlocked, boolean isBlockedByManager) {
        ContractsEntity contract = contractDAO.show(id);
        contract.setIsBlocked(isBlocked);
        contract.setIsBlockedByManager(isBlockedByManager);
        if (!contractValidation.validateBlocking(contract, isBlocked, isBlockedByManager)) {
            throw new BusinessLogicException("Failed to unblock contract.");
        }
        contractDAO.edit(contract);
    }
}
