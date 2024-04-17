# OAuth2 Spring Boot Authorization Server

## Tutorial Playlist (Persian Language)

This comprehensive tutorial series on our YouTube channel covers the fundamentals of OAuth 2 and various grant types, followed by step-by-step implementation guides for building an Authorization Server and a Resource Server.

### What You'll Learn:

- Understand the `principles` and workings of OAuth 2 authentication.
- Dive into different `grant types`, their purposes, and when to use them.
- Learn how to implement an Authorization Server using Spring Boot.
- Explore `custom grant types` to tailor authentication flows according to your requirements.
- Implement token generation with added authorities using `KeyPairGenerator`.
- Utilize custom `GrantedAuthority` in the Resource Server for flexible access control.
- Set up and integrate a `Resource Server` (Order Service) with the Authorization Server.
- Explore practical scenarios and best practices for securing your applications.

Whether you're a beginner looking to grasp OAuth 2 concepts or an experienced developer aiming to build secure authentication systems, this playlist provides a comprehensive guide to get you started and proficient in OAuth 2 implementation with Spring Boot.

[Watch the playlist](https://youtube.com/playlist?list=PL9DW8wFSPVHnb53q-IwX5Eo5ifAppAqxv&si=miAdWx4Qkl5zGc-U)

[<img src="https://github.com/AbbasTorabi/oauth2-spring-boot-authorization-server/assets/47711934/00853b67-0170-442b-ba0e-2812b0395f26">](https://youtube.com/playlist?list=PL9DW8wFSPVHnb53q-IwX5Eo5ifAppAqxv&si=miAdWx4Qkl5zGc-U)

## Technical Description
This repository contains two projects:
- **Authorization Server**: Implements OAuth 2 for authentication.
- **Resource Server (Order Service)**: Utilizes the Authorization Server to authorize requests.

### Important Implementations:
- **Custom Grant Type**: Customize the `/oauth2/token` endpoint to define custom grant types and necessary parameters for user or client authorization.
- **Custom Token Generation**: Customize token generation to include authorities in claims, generated using `KeyPairGenerator`.
- **Custom Authorities**: Use custom `GrantedAuthority` in the Resource Server to manage both scope and authorities as required.

## Installation and Setup
To install and set up the application, follow these steps:

1. Clone the repository.
2. Start the Authorization Server by navigating to its directory and running `./mvnw spring-boot:run`.
3. Start the Resource Server (Order Service) by navigating to its directory and running `./mvnw spring-boot:run`.
4. Import `OAuth 2.postman_collection.json` into Postman and explore the endpoints.

Note: Both servers (Authorization and Resource) follow the same setup process.

## License
This app is licensed under the MIT license. See the LICENSE file for more details.
