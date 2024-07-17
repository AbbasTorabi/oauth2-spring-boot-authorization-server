package com.example.oauth.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Client extends BaseEntity {

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    @Column(nullable = false)
    private String redirectUri;

    @Column(nullable = false)
    private Boolean requireProofKey;

    @Column(nullable = false)
    private ClientAuthenticationMethod authenticationMethod;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_scopes",
            joinColumns = { @JoinColumn(name = "client_id")},
            inverseJoinColumns = { @JoinColumn(name = "scope_id") }
    )
    private Set<Scope> scopes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_grant_types",
            joinColumns = { @JoinColumn(name = "client_id")},
            inverseJoinColumns = { @JoinColumn(name = "grant_type_id") }
    )
    private Set<GrantType> grantTypes;

    public List<AuthorizationGrantType> getAuthorizationGrantType() {
        List<AuthorizationGrantType> authorizationGrantTypes = new ArrayList<>();
        for (GrantType grantType : grantTypes) {
            authorizationGrantTypes.add(new AuthorizationGrantType((grantType.getName())));
        }
        return authorizationGrantTypes;
    }

}
