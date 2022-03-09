package com.example.application.dto;

import com.example.application.models.ClientsEntity;
import com.example.application.models.OptionsEntity;
import com.example.application.models.PlansEntity;
import lombok.Data;
import java.util.Set;

@Data
public class ContractDTO {
    private int id;

    private String phoneNumber;

    ClientsEntity client;

    PlansEntity plan;

    private boolean isBlocked;

    private boolean isBlockedByManager;

    Set<OptionsEntity> options;

    public boolean containsOption(int optionId) {
        for (OptionsEntity option : options) {
            if (option.getId() == optionId)
                return true;
        }
        return false;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean getIsBlockedByManager() {
        return isBlockedByManager;
    }

    public void setBlockedByManager(boolean blockedByManager) {
        isBlockedByManager = blockedByManager;
    }
}
