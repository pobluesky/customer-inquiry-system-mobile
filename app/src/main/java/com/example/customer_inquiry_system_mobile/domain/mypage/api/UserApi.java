package com.example.customer_inquiry_system_mobile.domain.mypage.api;

import com.example.customer_inquiry_system_mobile.domain.mypage.dto.LoginRequestDTO;
import com.example.customer_inquiry_system_mobile.domain.mypage.dto.LoginResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.mypage.dto.UserInfoResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/mobile/api/users/sign-in")
    Call<LoginResponseDTO> signIn(@Body LoginRequestDTO loginRequestDTO);

    @GET("/mobile/api/users/{userId}")
    Call<UserInfoResponseDTO> getUserInfo(
            @Header("Authorization") String token,
            @Path("userId") Long userId
    );
}
