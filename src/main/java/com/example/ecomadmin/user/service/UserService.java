package com.example.ecomadmin.user.service;

import com.example.ecomadmin.auth.dto.AuthUser;
import com.example.ecomadmin.user.dto.UserPasswordUpdateRequest;
import com.example.ecomadmin.user.dto.UserResponse;
import com.example.ecomadmin.user.dto.UserUpdateRequest;
import com.example.ecomadmin.user.dto.WithdrawRequest;
import com.example.ecomadmin.user.entity.User;
import com.example.ecomadmin.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );

        return new UserResponse(user.getId(), user.getEmail());
    }

    @Transactional
    public void updateUserEmail(AuthUser authUser, @Valid UserUpdateRequest dto) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        user.update(dto.getEmail());
    }

    @Transactional
    public void updatePassword(AuthUser authUser, UserPasswordUpdateRequest dto) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        if (dto.getNewPassword().equals(dto.getOldPassword())) {
            throw new IllegalStateException("새로운 비밀번호를 입력해주세요.");
        }

        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
    }

    @Transactional
    public void withdraw(AuthUser authUser, WithdrawRequest dto) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );

        if (user.getDeletedAt() != null) {
            throw new IllegalStateException("이미 탈퇴된 유저입니다.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        user.delete();
    }
}
