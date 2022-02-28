package com.example.application.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients", schema = "public", catalog = "postgres")
public class ClientsEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    @Getter
    @Setter
    private Date birthdate;

    @Column(name = "surname")
    @Getter
    @Setter
    private String surname;

    @Column(name = "passport")
    @Getter
    @Setter
    private String passport;

    @Column(name = "address")
    @Getter
    @Setter
    private String address;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Column(name = "password")
    @Getter
    @Setter
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.PERSIST)
    @Getter
    @Setter
    private List<ContractsEntity> contracts;

    @Override
    public String toString() {
        return "ClientsEntity[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", surname='" + surname + '\'' +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientsEntity that = (ClientsEntity) o;
        return id == that.id && name.equals(that.name) && birthdate.equals(that.birthdate) && surname.equals(that.surname) && passport.equals(that.passport) && address.equals(that.address) && email.equals(that.email) && password.equals(that.password) && contracts.equals(that.contracts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, surname, passport, address, email, password, contracts);
    }
}
