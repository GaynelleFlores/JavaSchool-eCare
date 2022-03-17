package com.example.application.validation;

import com.example.application.models.ContractsEntity;
import com.example.application.models.OptionsEntity;
import com.example.application.models.PlansEntity;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ContractValidationTest {

    private final ContractValidation validation = new ContractValidation();

    @Test
    public void validateContractIsBlocked() {
        ContractsEntity contract = new ContractsEntity();
        contract.setIsBlocked(true);
        assertFalse(validation.validateContract(contract));
        contract.setIsBlocked(false);
        assertTrue(validation.validateContract(contract));
    }

    @Test
    public void validateContractNotAllowedOptions() {
        ContractsEntity contract = new ContractsEntity();
        Set<OptionsEntity> allowedOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> contractOptions = new HashSet<OptionsEntity>();
        OptionsEntity allowedOption = new OptionsEntity(1);
        OptionsEntity notAllowedOption = new OptionsEntity(2);

        allowedOptions.add(allowedOption);
        contractOptions.add(allowedOption);
        contract.setOptions(contractOptions);
        contract.setPlan(new PlansEntity());
        contract.getPlan().setAllowedOptions(allowedOptions);

        assertTrue(validation.validateContract(contract));

        contract.getOptions().add(notAllowedOption);
        assertFalse(validation.validateContract(contract));
    }

    @Test
    public void validateContractIncompatibleOptions() {
        ContractsEntity contract = new ContractsEntity();
        Set<OptionsEntity> allowedOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> contractOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> incOptions = new HashSet<OptionsEntity>();
        OptionsEntity incompatibleOption = new OptionsEntity(1);

        incOptions.add(new OptionsEntity(2));
        incompatibleOption.setIncompatibleOptions(incOptions);
        allowedOptions.add(incompatibleOption);
        allowedOptions.add(new OptionsEntity(2));
        allowedOptions.add(new OptionsEntity(3));
        contractOptions.add(incompatibleOption);
        contractOptions.add(new OptionsEntity(3));
        contract.setOptions(contractOptions);
        contract.setPlan(new PlansEntity());
        contract.getPlan().setAllowedOptions(allowedOptions);

        assertTrue(validation.validateContract(contract));
        contract.getOptions().add(new OptionsEntity(2));
        assertFalse(validation.validateContract(contract));
    }

    @Test
    public void validateContractIncompatibleOptionsMirror() {
        ContractsEntity contract = new ContractsEntity();
        Set<OptionsEntity> allowedOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> contractOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> incOptions = new HashSet<OptionsEntity>();
        OptionsEntity incompatibleOption = new OptionsEntity(1);

        incOptions.add(new OptionsEntity(2));
        incompatibleOption.setIncompatibleOptionsMirror(incOptions);
        allowedOptions.add(incompatibleOption);
        allowedOptions.add(new OptionsEntity(2));
        allowedOptions.add(new OptionsEntity(3));
        contractOptions.add(incompatibleOption);
        contractOptions.add(new OptionsEntity(3));
        contract.setOptions(contractOptions);
        contract.setPlan(new PlansEntity());
        contract.getPlan().setAllowedOptions(allowedOptions);

        assertTrue(validation.validateContract(contract));
        contract.getOptions().add(new OptionsEntity(2));
        assertFalse(validation.validateContract(contract));
    }

    @Test
    public void validateContractRequiredOptions() {
        ContractsEntity contract = new ContractsEntity();
        Set<OptionsEntity> allowedOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> contractOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> reqOptions = new HashSet<OptionsEntity>();
        OptionsEntity requiredOption = new OptionsEntity(1);

        reqOptions.add(new OptionsEntity(2));
        requiredOption.setRequiredOptions(reqOptions);
        allowedOptions.add(requiredOption);
        allowedOptions.add(new OptionsEntity(2));
        allowedOptions.add(new OptionsEntity(3));
        contractOptions.add(requiredOption);
        contractOptions.add(new OptionsEntity(3));
        contract.setOptions(contractOptions);
        contract.setPlan(new PlansEntity());
        contract.getPlan().setAllowedOptions(allowedOptions);

        assertFalse(validation.validateContract(contract));
        contract.getOptions().add(new OptionsEntity(2));
        assertTrue(validation.validateContract(contract));
    }

    @Test
    public void validateContractRequiredOptionsMirror() {
        ContractsEntity contract = new ContractsEntity();
        Set<OptionsEntity> allowedOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> contractOptions = new HashSet<OptionsEntity>();
        Set<OptionsEntity> reqOptions = new HashSet<OptionsEntity>();
        OptionsEntity requiredOption = new OptionsEntity(1);

        reqOptions.add(new OptionsEntity(2));
        requiredOption.setRequiredOptionsMirror(reqOptions);
        allowedOptions.add(requiredOption);
        allowedOptions.add(new OptionsEntity(2));
        allowedOptions.add(new OptionsEntity(3));
        contractOptions.add(requiredOption);
        contractOptions.add(new OptionsEntity(3));
        contract.setOptions(contractOptions);
        contract.setPlan(new PlansEntity());
        contract.getPlan().setAllowedOptions(allowedOptions);

        assertFalse(validation.validateContract(contract));
        contract.getOptions().add(new OptionsEntity(2));
        assertTrue(validation.validateContract(contract));
    }
}