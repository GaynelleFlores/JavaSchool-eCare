package com.example.application.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "plans", schema = "public", catalog = "postgres")
public class PlansEntity {
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

    @ManyToMany
    @JoinTable(
            name = "plans_options",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    @Getter
    @Setter
    Set<OptionsEntity> allowed_options;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan", cascade = CascadeType.PERSIST)
    @Getter
    @Setter
    private List<ContractsEntity> contracts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlansEntity that = (PlansEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(price, that.price);
    }

    @Override
    public String toString() {
        return "PlansEntity[" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", allowed_options='" + allowed_options + '\'' +
                ", price=" + price +
                ']';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }
}
