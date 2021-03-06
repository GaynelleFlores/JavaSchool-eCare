package com.example.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
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
    private String phoneNumber;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "is_blocked_by_manager")
    private boolean isBlockedByManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="plan_id")
    @EqualsAndHashCode.Exclude
    PlansEntity plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    @EqualsAndHashCode.Exclude
    ClientsEntity client;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "contracts_options",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    Set<OptionsEntity> options;
}
