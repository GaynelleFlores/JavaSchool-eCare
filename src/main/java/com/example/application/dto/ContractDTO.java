package com.example.application.dto;

import com.example.application.models.OptionsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.Set;

@Data
public class ContractDTO {
    private int id;

    private String phoneNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    ClientDTO client;

    @EqualsAndHashCode.Exclude
    PlanDTO plan;

    private boolean isBlocked;

    private boolean isBlockedByManager;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    Set<OptionsEntity> options;
}
