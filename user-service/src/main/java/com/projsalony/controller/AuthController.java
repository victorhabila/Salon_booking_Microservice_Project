package com.projsalony.controller;

import com.projsalony.payload.response.ApiResponse;
import com.projsalony.payload.response.ApiResponseBody;
import com.projsalony.payload.response.AuthResponse;
import com.projsalony.payload.response.request.LoginDto;
import com.projsalony.payload.response.request.SignupDTO;
import com.projsalony.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> HomeControllerHandler() {

        return ResponseEntity.status(
                        HttpStatus.OK)
                .body(new ApiResponse(

                        "welcome to Salony booking system, user api"

                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(
            @RequestBody SignupDTO req) throws Exception {

        System.out.println("signup dto "+req);
        AuthResponse response=authService.signup(req);

        return ResponseEntity.ok( response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody LoginDto req) throws Exception {

        AuthResponse response=authService.login(req.getEmail(), req.getPassword());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<AuthResponse> getAccessTokenHandler(
            @PathVariable String refreshToken) throws Exception {
        AuthResponse response = authService.getAccessTokenFromRefreshToken(refreshToken);
        return ResponseEntity.ok(
                response
        );
    }


}
