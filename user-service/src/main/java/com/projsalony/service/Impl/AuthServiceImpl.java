package com.projsalony.service.Impl;

import com.projsalony.model.User;
import com.projsalony.payload.response.AuthResponse;
import com.projsalony.payload.response.TokenResponse;
import com.projsalony.payload.response.request.SignupDTO;
import com.projsalony.repository.UserRepository;
import com.projsalony.service.AuthService;
import com.projsalony.service.KeycloakUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;

    public AuthServiceImpl(UserRepository userRepository, KeycloakUserService keycloakUserService) {
        this.userRepository = userRepository;
        this.keycloakUserService = keycloakUserService;
    }

    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse=keycloakUserService.getAdminAccessToken(
                username,
                password,
                "password",
                null
        );
        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome Back " + username);
        response.setMessage("login success");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;

    }

    @Override
    public AuthResponse signup(SignupDTO req) throws Exception {
        keycloakUserService.createUser(req);

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setPhone(req.getPhone());
        createdUser.setRole(req.getRole());
        createdUser.setFullName(req.getFullName());
        createdUser.setUsername(req.getUsername());
        userRepository.save(createdUser);


        TokenResponse tokenResponse= keycloakUserService.getAdminAccessToken(
                req.getUsername(),
                req.getPassword(),
                "password",
                null
        );

        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome " + createdUser.getEmail());
        response.setMessage("Register success");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;

    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse= keycloakUserService.getAdminAccessToken(
                null,
                null,
                "refresh_token",
                refreshToken
        );
        AuthResponse response = new AuthResponse();

        response.setMessage("Access token received");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;

    }
}
