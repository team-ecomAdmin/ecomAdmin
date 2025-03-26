package com.example.ecomadmin.user.controller;

import com.example.ecomadmin.auth.dto.AuthUser;
import com.example.ecomadmin.user.dto.WithdrawRequest;
import com.example.ecomadmin.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody WithdrawRequest dto) {
        userService.withdraw(authUser, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
