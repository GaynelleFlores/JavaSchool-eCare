package com.example.application.dto;

import com.example.application.models.ContractsEntity;
import com.example.application.models.OptionsEntity;
import com.example.application.models.PlansEntity;
import lombok.Data;
import java.math.BigInteger;
import java.util.Set;

@Data
public class OptionDTO {

    private int id;

    private String title;

    private BigInteger price;

    private BigInteger connectionCost;

    Set<ContractsEntity> contracts;

    Set<PlansEntity> plans;

    Set<OptionsEntity> incompatibleOptions;

    Set<OptionsEntity> incompatibleOptionsMirror;

    Set<OptionsEntity> requiredOptions;

    Set<OptionsEntity> requiredOptionsMirror;
}
