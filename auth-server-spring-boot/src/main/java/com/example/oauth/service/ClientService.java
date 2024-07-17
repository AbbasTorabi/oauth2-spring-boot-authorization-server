package com.example.oauth.service;

import com.example.oauth.entity.Client;
import com.example.oauth.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public interface ClientService extends RegisteredClientRepository {

    Client create(Client client);

    Client getClientByClientId(String clientId);

    @Override
    void save(RegisteredClient registeredClient);

    @Override
    RegisteredClient findById(String id);

    @Override
    RegisteredClient findByClientId(String clientId);

}
