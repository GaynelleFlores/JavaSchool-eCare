package com.example.application.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "options", schema = "public", catalog = "postgres")
public class OptionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "title")
    @Getter
    @Setter
    private String title;

    @Column(name = "price")
    @Getter
    @Setter
    private BigInteger price;

    @Column(name = "connection_cost")
    @Getter
    @Setter
    private BigInteger connection_cost;


    @ManyToMany(mappedBy = "options")
    @Getter
    @Setter
    Set<ContractsEntity> contracts;

    @ManyToMany(mappedBy = "allowed_options")
    @Getter
    @Setter
    Set<PlansEntity> plans;

    @Override
    public String toString() {
        return "OptionsEntity[" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", connection_cost=" + connection_cost +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionsEntity that = (OptionsEntity) o;
        return id == that.id && title.equals(that.title) && price.equals(that.price) && connection_cost.equals(that.connection_cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, connection_cost);
    }
}
