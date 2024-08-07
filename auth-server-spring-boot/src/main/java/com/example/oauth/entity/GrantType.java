package com.example.oauth.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "grant_type")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GrantType extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "grantTypes")
    private Set<Client> clients;
}
