package com.example.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "clients", schema = "public", catalog = "postgres")
public class ClientsEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "surname")
    private String surname;

    @Column(name = "passport")
    private char[] passport;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @EqualsAndHashCode.Exclude
    private List<ContractsEntity> contracts;
}
