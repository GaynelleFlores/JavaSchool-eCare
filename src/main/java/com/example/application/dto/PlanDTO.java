package com.example.application.dto;

import com.example.application.models.ContractsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Data
public class PlanDTO {

    private int id;

    private String title;

    private BigInteger price;

    @JsonIgnore
    Set<OptionDTO> allowedOptions;

    @JsonIgnore
    private List<ContractsEntity> contracts;
}
