package com.example.application.validation;

import com.example.application.config.LoggerConfig;
import com.example.application.models.ContractsEntity;
import com.example.application.models.OptionsEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashSet;
import java.util.Set;

public class ContractValidation {

    private final Logger logger;

    {
        this.logger = LogManager.getLogger(LoggerConfig.class);
    }

    private boolean checkOptionsIsAllowed(ContractsEntity contract) {
        if (contract.getPlan().getAllowedOptions() == null && contract.getOptions().size() > 0) {
            logger.error("Contract  contains not allowed option.");
            return false;
        }
        for (OptionsEntity option : contract.getOptions()) {
            if (!contract.getPlan().getAllowedOptions().contains(option)) {
                logger.error("Contract contains not allowed option.");
                return false;
            }
        }
        return true;
    }

    private boolean checkRequiredOptions(Set<OptionsEntity> required, Set<OptionsEntity> contractsOptions) {
        if (required == null || contractsOptions == null) {
            return true;
        }
        return !required.retainAll(contractsOptions);
    }

    private boolean checkIncompatibleOptions(Set<OptionsEntity> incompatible, Set<OptionsEntity> contractsOptions) {
        if (incompatible == null || contractsOptions == null) {
            return true;
        }
        return !incompatible.removeAll(contractsOptions);
    }

    private Set<OptionsEntity> createTempSet(Set<OptionsEntity> first, Set<OptionsEntity> second) {
        Set<OptionsEntity> temp = new HashSet<OptionsEntity>();
        if (first != null) {
            temp.addAll(first);
        }
        if (second != null) {
            temp.addAll(second);
        }
        return temp;
    }

    private boolean checkOptions(ContractsEntity contract) {
        for (OptionsEntity option : contract.getOptions()) {
            Set<OptionsEntity> temp = createTempSet(option.getIncompatibleOptions(), option.getIncompatibleOptionsMirror());
            if (!checkIncompatibleOptions(temp, contract.getOptions())) {
                logger.error("Contract contains incompatible options");
                return false;
            }
            temp = createTempSet(option.getRequiredOptions(), option.getRequiredOptionsMirror());
            if (!checkRequiredOptions(temp, contract.getOptions())) {
                logger.error("Contract doesn't contain required options");
                return false;
            }
        }
        return true;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        String regexp = "8\\d{4}";
        if (phoneNumber == null)
            return false;
        return phoneNumber.matches(regexp);
    }

    public boolean validateContract(ContractsEntity contract) {
        if (contract.getIsBlocked()) {
            logger.error("Contract is blocked, it is impossible to edit it.");
            return false;
        }
        if (!validatePhoneNumber(contract.getPhoneNumber())) {
            logger.error("Phone number is incorrect.");
            return false;
        }
        if (contract.getPlan() == null) {
            logger.error("Contract has no plan.");
            return false;
        }
        if (contract.getOptions() == null) {
            return true;
        }
        return checkOptionsIsAllowed(contract) && checkOptions(contract);
    }

    public boolean validateBlocking(ContractsEntity contract, boolean isBlocked, boolean isBlockedByManager) {
        if (!isBlocked) {
            if (contract.getIsBlockedByManager() && !isBlockedByManager) {
                logger.error("Contract was blocked by manager, client can't unblock it.");
                return false;
            }
        }
        return true;
    }

}
