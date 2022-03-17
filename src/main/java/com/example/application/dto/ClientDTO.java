package com.example.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@ToString(exclude = { "contracts"})
public class ClientDTO {

    private int id;

    private String name;

    private Date birthdate;

    private String surname;

    private String passport;

    private String address;

    private String email;

    private char[] password;

    @JsonIgnore
    private List<ContractDTO> contracts;
}
