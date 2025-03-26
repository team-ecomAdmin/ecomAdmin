package com.example.ecomadmin.user.controller;

import com.example.ecomadmin.auth.dto.AuthUser;
import com.example.ecomadmin.user.dto.UserPasswordUpdateRequest;
import com.example.ecomadmin.user.dto.UserResponse;
import com.example.ecomadmin.user.dto.UserUpdateRequest;
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser (
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping
    public ResponseEntity<Void> updateUserEmail (
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserUpdateRequest dto
    ) {
        userService.updateUserEmail(authUser, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword (
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserPasswordUpdateRequest dto
    ) {
        userService.updatePassword(authUser, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody WithdrawRequest dto) {
        userService.withdraw(authUser, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
