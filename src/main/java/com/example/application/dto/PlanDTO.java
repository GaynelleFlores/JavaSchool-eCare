package com.example.application.dto;

import com.example.application.models.ContractsEntity;
import com.example.application.models.OptionsEntity;
import lombok.Data;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Data
public class PlanDTO {

    private int id;

    private String title;

    private BigInteger price;

    Set<OptionsEntity> allowedOptions;

    private List<ContractsEntity> contracts;
}
