package com.example.ecomadmin.auth.service;

import com.example.ecomadmin.auth.dto.AuthRequest;
import com.example.ecomadmin.auth.dto.SigninResponse;
import com.example.ecomadmin.common.JwtUtil;
import com.example.ecomadmin.user.entity.User;
import com.example.ecomadmin.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(AuthRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = new User(dto.getEmail(), encodedPassword);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public SigninResponse signin(@Valid AuthRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new IllegalStateException("사용자를 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail());

        return new SigninResponse(bearerToken);
    }
}
