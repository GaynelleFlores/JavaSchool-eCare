package com.example.application.dto;

import com.example.application.models.ContractsEntity;
import lombok.Data;
import java.math.BigInteger;
import java.util.List;

@Data
public class OptionDTO {

    private int id;

    private String title;

    private BigInteger price;

    private BigInteger connection_cost;

    List<ContractsEntity> contracts;
}
