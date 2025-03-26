package com.example.ecomadmin.user.service;

import com.example.ecomadmin.auth.dto.AuthUser;
import com.example.ecomadmin.user.dto.WithdrawRequest;
import com.example.ecomadmin.user.entity.User;
import com.example.ecomadmin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
