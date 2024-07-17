package com.example.oauth.service;

import com.example.oauth.entity.Client;
import com.example.oauth.entity.Scope;
import com.example.oauth.entity.User;
import com.example.oauth.repository.ClientRepository;
import com.example.oauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public Client create(Client client) {
        client.assignId();
        Client createdClient = repository.save(client);
        return createdClient;
    }

    @Override
    public Client getClientByClientId(String clientId) {
        try {
            return repository.getClientByClientId(clientId).orElseThrow();
         } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        // TODO: Must convert RegisteredClient -> Client
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = repository.findById(id).orElseThrow();
        return createClientBuilder(client).build();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = repository.getClientByClientId(clientId).orElseThrow();
        return createClientBuilder(client).build();
    }

    private RegisteredClient.Builder createClientBuilder(Client client) {
        return RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(method -> method.addAll(List.of(client.getAuthenticationMethod(), ClientAuthenticationMethod.NONE)))
                .redirectUri(client.getRedirectUri())
                .authorizationGrantTypes(grantType -> grantType.addAll(client.getAuthorizationGrantType()))
                .scopes(scope -> scope.addAll(client.getScopes().stream().map(Scope::getName).toList()))
                .clientSettings(ClientSettings.builder().requireProofKey(client.getRequireProofKey()).build())
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(24)).build()); // Customize Token settings
    }

}
