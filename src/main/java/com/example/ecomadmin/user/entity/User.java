package com.example.ecomadmin.user.entity;

import com.example.ecomadmin.common.BaseTimeEntity;
import com.example.ecomadmin.common.Const;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void update(String email) {
        this.email = email;
    }

    public void updatePassword(String encode) {
        this.password = encode;
    }
}
