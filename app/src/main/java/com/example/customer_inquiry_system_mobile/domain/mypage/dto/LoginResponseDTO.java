package com.example.customer_inquiry_system_mobile.domain.mypage.dto;

public class LoginResponseDTO {
    private String accessToken;
    private Long userId; // userId 필드 추가

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

