package com.example.orderservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Extract authorities
        Collection<String> authoritiesStringCollection = jwt.getClaimAsStringList("authorities");
        if (authoritiesStringCollection != null) {
            for (String authority : authoritiesStringCollection) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        // Extract scopes
        Collection<String> scopesStringCollection = jwt.getClaimAsStringList("scope");
        if (scopesStringCollection != null) {
            for (String scope : scopesStringCollection) {
                authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
            }
        }

        return authorities;
    }

}
