package com.example.application.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Data
@Table(name = "options", schema = "public", catalog = "postgres")
public class OptionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigInteger price;

    @Column(name = "connection_cost")
    private BigInteger connection_cost;

    @ManyToMany(mappedBy = "options")
    @EqualsAndHashCode.Exclude
    Set<ContractsEntity> contracts;

    @ManyToMany(mappedBy = "allowed_options")
    @EqualsAndHashCode.Exclude
    Set<PlansEntity> plans;
}
