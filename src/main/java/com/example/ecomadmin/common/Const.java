package com.example.ecomadmin.common;

public interface Const {

    // 최소 8자, 대문자 1개 이상, 소문자 1개 이상, 숫자 1개 이상, 특수문자(!@#$%^&*) 중 1개 이상
    String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$";
    String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
}

