package com.example.oauth.repository;

import com.example.oauth.entity.Client;
import com.example.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> getClientByClientId(String clientId);

}
