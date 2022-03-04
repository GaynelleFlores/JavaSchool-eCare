package com.example.application.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@ToString(exclude = { "options", "client", "plan"})
@Table(name = "contracts", schema = "public", catalog = "postgres")
public class ContractsEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "is_blocked")
    private boolean is_blocked;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="plan_id")
    @EqualsAndHashCode.Exclude
    PlansEntity plan;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="client_id")
    @EqualsAndHashCode.Exclude
    ClientsEntity client;

    @ManyToMany
    @JoinTable(
            name = "contracts_options",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    Set<OptionsEntity> options;
}
