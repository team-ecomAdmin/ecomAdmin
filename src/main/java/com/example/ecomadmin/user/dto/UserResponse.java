package com.example.ecomadmin.user.dto;

import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;

    public UserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
