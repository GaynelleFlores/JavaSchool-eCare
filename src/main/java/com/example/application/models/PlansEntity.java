package com.example.application.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "plans", schema = "public", catalog = "postgres")
public class PlansEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigInteger price;

    @ManyToMany
    @JoinTable(
            name = "plans_options",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<OptionsEntity> allowedOptions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ContractsEntity> contracts;
}
