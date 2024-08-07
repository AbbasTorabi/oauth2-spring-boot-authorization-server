package com.example.oauth.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "scope")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Scope extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "scopes")
    private Set<Client> clients;
}
