package com.example.application.models;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "options", schema = "public", catalog = "postgres")
public class OptionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "price")
    private BigInteger price;
    @Basic
    @Column(name = "connectionCost")
    private BigInteger connectionCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public BigInteger getConnectionCost() {
        return connectionCost;
    }

    public void setConnectionCost(BigInteger connectionCost) {
        this.connectionCost = connectionCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionsEntity that = (OptionsEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(connectionCost, that.connectionCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, connectionCost);
    }
}
