package com.example.application.dto;

import com.example.application.models.ContractsEntity;
import com.example.application.models.OptionsEntity;
import com.example.application.models.PlansEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.math.BigInteger;
import java.util.Set;

@Data
public class OptionDTO {

    private int id;

    private String title;

    private BigInteger price;

    private BigInteger connectionCost;

    @JsonIgnore
    Set<ContractsEntity> contracts;

    @JsonIgnore
    Set<PlansEntity> plans;

    @JsonIgnore
    Set<OptionsEntity> incompatibleOptions;

    @JsonIgnore
    Set<OptionsEntity> incompatibleOptionsMirror;

    @JsonIgnore
    Set<OptionsEntity> requiredOptions;

    @JsonIgnore
    Set<OptionsEntity> requiredOptionsMirror;
}
