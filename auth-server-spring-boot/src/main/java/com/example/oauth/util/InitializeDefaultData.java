package com.example.oauth.util;

import com.example.oauth.entity.*;
import com.example.oauth.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitializeDefaultData implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final RoleService roleService;
    private final GrantTypeService grantTypeService;
    private final ScopeService scopeService;
    private final ClientService clientService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // region Create User
        Role superAdmin;
        Role contentAdmin;

        superAdmin = roleService.findByName("ROLE_SUPER_ADMIN");

        if (superAdmin == null) {

            superAdmin = roleService.create(
                    Role.builder()
                            .name("ROLE_SUPER_ADMIN")
                            .build()
            );

        }

        contentAdmin = roleService.findByName("ROLE_CONTENT_ADMIN");

        if (contentAdmin == null) {

            contentAdmin = roleService.create(
                    Role.builder()
                            .name("ROLE_CONTENT_ADMIN")
                            .build()
            );

        }

        User abbas = userService.findByMobile("091212345");

        if (abbas == null) {

            userService.create(
                    User.builder()
                            .name("Abbas")
                            .mobile("091212345")
                            .password("123456")
                            .roles(Set.of(superAdmin, contentAdmin))
                            .build()
            );

        }

        // endregion

        // region Create Client
        GrantType ropc;
        GrantType authorizationCode;
        GrantType clientCredentials;
        Scope openid;
        Scope profile;

        ropc = grantTypeService.findByName("ropc");

        if (ropc == null) {

            ropc = grantTypeService.create(
                    GrantType.builder()
                            .name("ropc")
                            .build()
            );

        }

        authorizationCode = grantTypeService.findByName("authorization_code");

        if (authorizationCode == null) {

            authorizationCode = grantTypeService.create(
                    GrantType.builder()
                            .name("authorization_code")
                            .build()
            );

        }

        clientCredentials = grantTypeService.findByName("client_credentials");

        if (clientCredentials == null) {

            clientCredentials = grantTypeService.create(
                    GrantType.builder()
                            .name("client_credentials")
                            .build()
            );

        }

        openid = scopeService.findByName("openid");

        if (openid == null) {

            openid = scopeService.create(
                    Scope.builder()
                            .name("openid")
                            .build()
            );

        }

        profile = scopeService.findByName("profile");

        if (profile == null) {

            profile = scopeService.create(
                    Scope.builder()
                            .name("profile")
                            .build()
            );

        }

        Client orderService = clientService.getClientByClientId("order-service");

        if (orderService == null) {

            clientService.create(
                    Client.builder()
                            .clientId("order-service")
                            .clientSecret("order-secret")
                            .redirectUri("http://127.0.0.1:4200/login/oauth2/code/order_service")
                            .requireProofKey(true)
                            .authenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                            .scopes(Set.of(openid, profile))
                            .grantTypes(Set.of(ropc, authorizationCode, clientCredentials))
                            .build());
        }

        // endregion

    }

}
