package com.example.application.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Data
@Table(name = "options", schema = "public", catalog = "postgres")
@NoArgsConstructor
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
    private BigInteger connectionCost;

    @ManyToMany(mappedBy = "options")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<ContractsEntity> contracts;

    @ManyToMany(mappedBy = "allowedOptions")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<PlansEntity> plans;

    @ManyToMany
    @JoinTable(
            name = "incompatible_options",
            joinColumns = @JoinColumn(name = "first_id"),
            inverseJoinColumns = @JoinColumn(name = "second_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<OptionsEntity> incompatibleOptions;

    @ManyToMany
    @JoinTable(
            name = "incompatible_options",
            joinColumns = @JoinColumn(name = "second_id"),
            inverseJoinColumns = @JoinColumn(name = "first_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<OptionsEntity> incompatibleOptionsMirror;

    @ManyToMany
    @JoinTable(
            name = "required_options",
            joinColumns = @JoinColumn(name = "first_id"),
            inverseJoinColumns = @JoinColumn(name = "second_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<OptionsEntity> requiredOptions;

    @ManyToMany
    @JoinTable(
            name = "required_options",
            joinColumns = @JoinColumn(name = "second_id"),
            inverseJoinColumns = @JoinColumn(name = "first_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<OptionsEntity> requiredOptionsMirror;

    public OptionsEntity(int id) {
        this.id = id;
    }
}
