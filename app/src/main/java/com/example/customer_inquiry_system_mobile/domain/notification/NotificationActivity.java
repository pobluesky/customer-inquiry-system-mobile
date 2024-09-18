package com.example.customer_inquiry_system_mobile.domain.notification;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.notification.adapter.NotificationAdapter;
import com.example.customer_inquiry_system_mobile.domain.notification.api.NotificationApi;
import com.example.customer_inquiry_system_mobile.domain.notification.dto.NotificationResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private NotificationAdapter adapter;

    private List<NotificationResponseDTO> notifications = new ArrayList<>();

    private NotificationApi notificationApi;

    private Long userId;

    private Button buttonUnread;

    private Button buttonRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService retrofitService = new RetrofitService(null);
        notificationApi = retrofitService.getNotificationApi();

        adapter = new NotificationAdapter(notifications, retrofitService);
        recyclerView.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        userId = getUserIdFromPreferences();

        buttonUnread = findViewById(R.id.buttonUnread);
        buttonRead = findViewById(R.id.buttonRead);

        buttonUnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonState(buttonUnread);
                fetchNotifications(true);
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonState(buttonRead);
                fetchNotifications(false);
            }
        });

        ImageView revertIcon = findViewById(R.id.revertIcon);
        revertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateButtonState(buttonUnread);
        fetchNotifications(true);
    }

    private void updateButtonState(Button clickedButton) {
        if (clickedButton == buttonUnread) {
            buttonUnread.setTextColor(Color.parseColor("#8A9DD0"));
            buttonRead.setTextColor(Color.parseColor("#C1C1C1"));

            findViewById(R.id.viewUnread).setBackgroundColor(Color.parseColor("#8A9DD0"));
            findViewById(R.id.viewRead).setBackgroundColor(Color.parseColor("#C1C1C1"));
        } else if (clickedButton == buttonRead) {
            buttonUnread.setTextColor(Color.parseColor("#C1C1C1"));
            buttonRead.setTextColor(Color.parseColor("#8A9DD0"));

            findViewById(R.id.viewUnread).setBackgroundColor(Color.parseColor("#C1C1C1"));
            findViewById(R.id.viewRead).setBackgroundColor(Color.parseColor("#8A9DD0"));
        }
    }

    private void fetchNotifications(boolean unread) {
        Call<List<NotificationResponseDTO>> call;
        if (unread) {
            call = notificationApi.getUnreadNotifications(userId);
        } else {
            call = notificationApi.getReadNotifications(userId);
        }

        call.enqueue(new Callback<List<NotificationResponseDTO>>() {
            @Override
            public void onResponse(
                    Call<List<NotificationResponseDTO>> call,
                    Response<List<NotificationResponseDTO>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NotificationResponseDTO> fetchedNotifications = response.body();

                    notifications.clear();
                    notifications.addAll(fetchedNotifications);

                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(
                            "NotificationsActivity",
                            "Error fetching notifications: " + response.message()
                    );
                }
            }

            @Override
            public void onFailure(Call<List<NotificationResponseDTO>> call, Throwable t) {
                Log.e("NotificationsActivity", "Network error: " + t.getMessage());
            }
        });
    }

    private Long getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        return sharedPreferences.getLong("userId", -1L);
    }
}
