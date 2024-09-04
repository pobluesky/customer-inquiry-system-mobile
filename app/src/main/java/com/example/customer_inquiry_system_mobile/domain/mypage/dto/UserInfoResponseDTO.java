package com.example.customer_inquiry_system_mobile.domain.mypage.dto;

import com.google.gson.annotations.SerializedName;

public class UserInfoResponseDTO {

    @SerializedName("userId")
    private Long userId;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("empNo")
    private String empNo;

    @SerializedName("role")
    private String role;

    @SerializedName("department")
    private String department;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmpNo() {
        return empNo;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }
}
