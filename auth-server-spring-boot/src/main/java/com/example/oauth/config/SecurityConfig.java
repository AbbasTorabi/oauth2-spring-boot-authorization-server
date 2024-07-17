package com.example.oauth.config;

import com.example.oauth.security.CustomGrantAuthenticationConverter;
import com.example.oauth.security.CustomGrantAuthenticationProvider;
import com.example.oauth.service.UserService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainForOauth(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults())
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint.accessTokenRequestConverter(new CustomGrantAuthenticationConverter())
                        .authenticationProvider(new CustomGrantAuthenticationProvider(oAuth2AuthorizationService(), tokenGenerator(), userService, passwordEncoder())));
        httpSecurity.exceptionHandling(e -> e.authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/login")
        ));
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain appSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                        request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    // Moved To DB
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var admin = User.withUsername("admin")
//                .password("123456")
//                .authorities("read", "write")
//                .roles("Admin")
//                .build();
//        var abbas = User.withUsername("abbas")
//                .password("123456")
//                .authorities("read")
//                .roles("User")
//                .build();
//        return new InMemoryUserDetailsManager(admin, abbas);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Moved To DB
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        var productService = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("order-service")
//                .clientSecret("order-secret")
//                .scope(OidcScopes.PROFILE)
//                .scope(OidcScopes.OPENID)
//                .redirectUri("http://127.0.0.1:4200/login/oauth2/code/order_service")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantTypes(
//                        grantType -> {
//                            grantType.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
//                            grantType.add(AuthorizationGrantType.AUTHORIZATION_CODE);
//                            grantType.add(AuthorizationGrantType.REFRESH_TOKEN);
//                            grantType.add(new AuthorizationGrantType("ropc"));
//                        }
//                )
//                .clientSettings(ClientSettings.builder().requireProofKey(true).build())
//                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(24)).build()) // Customize Token settings
//                .build();
//        return new InMemoryRegisteredClientRepository(productService);
//    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            var keys = keyPairGenerator.generateKeyPair();
            var publicKey = (RSAPublicKey) keys.getPublic();
            var privateKey = keys.getPrivate();

            var rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();

            JWKSet jwkSet = new JWKSet(rsaKey);
            return new ImmutableJWKSet<>(jwkSet);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator() {
        NimbusJwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource());
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(tokenCustomizer());
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            Authentication principal = context.getPrincipal();
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
                    Set<String> scopes = new HashSet<>(context.getRegisteredClient().getScopes());
                    context.getClaims().claim("scopes", scopes);
                } else {
                    Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet());
                    context.getClaims().claim("authorities", authorities);
                }
            }

            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims().claim("authorities", authorities);
            }
        };
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


    @Bean
    OAuth2AuthorizationService oAuth2AuthorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

}
