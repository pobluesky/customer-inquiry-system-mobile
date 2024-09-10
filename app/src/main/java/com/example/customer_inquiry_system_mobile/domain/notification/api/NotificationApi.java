package com.example.customer_inquiry_system_mobile.domain.notification.api;

import com.example.customer_inquiry_system_mobile.domain.notification.dto.NotificationResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotificationApi {

    @GET("mobile/api/notifications/{userId}")
    Call<List<NotificationResponseDTO>> getUnreadNotifications(@Path("userId") Long userId);

    @GET("mobile/api/notifications/read/{userId}")
    Call<List<NotificationResponseDTO>> getReadNotifications(@Path("userId") Long userId);

    @PUT("mobile/api/notifications/{notificationId}")
    Call<Void> updateNotificationIsRead(@Path("notificationId") Long notificationId);
}

