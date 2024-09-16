package com.example.customer_inquiry_system_mobile.domain.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationHelper {

    public static final String CHANNEL_ID_HEADS_UP = "heads_up_channel_id";

    public static void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel headsUpChannel = new NotificationChannel(
                    CHANNEL_ID_HEADS_UP,
                    "Heads-Up Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            headsUpChannel.setDescription("Heads-Up notifications");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(headsUpChannel);
        }
    }
}
