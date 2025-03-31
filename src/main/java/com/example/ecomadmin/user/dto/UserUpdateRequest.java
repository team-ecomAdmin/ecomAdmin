package com.example.ecomadmin.user.dto;

import com.example.ecomadmin.common.Const;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @Email
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Pattern(
            regexp = Const.EMAIL_PATTERN,
            message = "이메일 형식이 올바르지 않습니다."
    )
    private String email;

    @NotBlank(message = "비밃번호 입력은 필수입니다.")
    @Size(min = 8)
    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호 형식이 올바르지 않습니다."
    )
    private String password;
}
