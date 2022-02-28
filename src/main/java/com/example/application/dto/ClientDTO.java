package com.example.application.dto;

import com.example.application.models.ContractsEntity;
import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
public class ClientDTO {

    private int id;

    private String name;

    private Date birthdate;

    private String surname;

    private String passport;

    private String address;

    private String email;

    private String password;

    private List<ContractsEntity> contracts;
}
