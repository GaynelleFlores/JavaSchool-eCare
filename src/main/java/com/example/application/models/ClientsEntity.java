package com.example.application.models;

import javax.persistence.*;
import java.util.Objects;

@Entity

@Table(name = "clients", schema = "public", catalog = "postgres")
public class ClientsEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rev_id_generator")
//    @SequenceGenerator(name = "rev_id_generator", sequenceName = "rev_id_seq", allocationSize = 1)

    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "birthdate")
    private String birthdate;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "passport")
    private String passport;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientsEntity that = (ClientsEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(birthdate, that.birthdate) && Objects.equals(surname, that.surname) && Objects.equals(passport, that.passport) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, surname, passport, address, email, password);
    }
}
