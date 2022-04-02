package com.example.application.dto;

import com.example.application.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Data
public class ClientDTO {

    private int id;

    private String name;

    private Date birthdate;

    private String surname;

    private String passport;

    private String address;

    private String email;

    private char[] password;

    private String login;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ContractDTO> contracts;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Role> roles;
}
