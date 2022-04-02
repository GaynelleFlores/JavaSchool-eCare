package com.example.application.models;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "roles", schema = "public", catalog = "postgres")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ClientsEntity> clients;
}