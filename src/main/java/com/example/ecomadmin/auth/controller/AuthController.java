package com.example.ecomadmin.auth.controller;

import com.example.ecomadmin.auth.dto.AuthRequest;
import com.example.ecomadmin.auth.dto.SigninResponse;
import com.example.ecomadmin.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody AuthRequest dto) {
        authService.signup(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@Valid @RequestBody AuthRequest dto) {
        SigninResponse signinResponse = authService.signin(dto);

        return ResponseEntity.ok()
                .header("Authorization", signinResponse.getBearerToken())
                .body(signinResponse);}
    }
