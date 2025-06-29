package com.projsalony.service;

import com.projsalony.payload.response.AuthResponse;
import com.projsalony.payload.response.request.SignupDTO;


public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;

}
