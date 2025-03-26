package com.example.ecomadmin.auth.dto;

import lombok.Getter;

@Getter
public class SigninResponse {
    private final String bearerToken;

    public SigninResponse(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
