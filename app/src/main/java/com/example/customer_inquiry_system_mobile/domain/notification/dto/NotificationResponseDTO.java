package com.example.customer_inquiry_system_mobile.domain.notification.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponseDTO {

    @SerializedName("notificationId")
    @Expose
    private Long notificationId;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("notificationContents")
    @Expose
    String notificationContents;

    @SerializedName("isRead")
    @Expose
    protected Boolean isRead = false;

    public Long getNotificationId() {
        return notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNotificationContents() {
        return notificationContents;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void read() {
        isRead = true;
    }
}
