package com.example.customer_inquiry_system_mobile.domain.mypage.dto;

public class LoginRequestDTO {

    private String email;

    private String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
