package com.example.application.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "contracts", schema = "public", catalog = "postgres")
public class ContractsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "phone_number")
    @Getter
    @Setter
    private String phone_number;

    @Column(name = "is_blocked")
    private boolean is_blocked;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="plan_id")
    @Getter
    @Setter
    PlansEntity plan;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="client_id")
    @Getter
    @Setter
    ClientsEntity client;

    @ManyToMany
    @JoinTable(
            name = "contracts_options",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    Set<OptionsEntity> options;

    public boolean getIs_blocked() {
        return this.is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    @Override
    public String toString() {
        return "ContractsEntity[" +
                "id=" + id +
                ", phone_number='" + phone_number + '\'' +
                ", options='" + options + '\'' +
                ", plan=" + plan + '\'' +
                ", client=" + client +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractsEntity that = (ContractsEntity) o;
        return id == that.id && is_blocked == that.is_blocked && phone_number.equals(that.phone_number) && plan.equals(that.plan) && client.equals(that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone_number, is_blocked);
    }
}
