package com.example.customer_inquiry_system_mobile.domain.notification.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.notification.api.NotificationApi;
import com.example.customer_inquiry_system_mobile.domain.notification.dto.NotificationResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.List;

import retrofit2.Call;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationResponseDTO> notifications;
    private RetrofitService retrofitService;

    public NotificationAdapter(
            List<NotificationResponseDTO> notifications,
            RetrofitService retrofitService
    ) {
        this.notifications = notifications;
        this.retrofitService = retrofitService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_notification_item,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationResponseDTO notification = notifications.get(position);

        holder.imageViewBell.setImageResource(R.drawable.bell_unread);
        holder.contentTextView.setText(notification.getNotificationContents());
        holder.imageViewChecked.setImageResource(R.drawable.checked);
        holder.itemView.setBackgroundResource(R.drawable.rounded_border_noti);

        if (notification.getRead()) {
            holder.imageViewBell.setImageResource(R.drawable.bell_read);
            holder.contentTextView.setText(notification.getNotificationContents());
            holder.contentTextView.setTextColor(Color.parseColor("#C1C1C1"));
            holder.imageViewChecked.setImageResource(R.drawable.unchecked);
            holder.itemView.setBackgroundResource(R.drawable.rounded_border_noti_read);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.read();

                NotificationApi notificationApi = retrofitService.getNotificationApi();

                Call<Void> call = notificationApi.updateNotificationIsRead(notification.getNotificationId());
                call.enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()) notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d(" APT_RESPONSE_FAILURE","API 호출 실패");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBell;
        TextView contentTextView;
        ImageView imageViewChecked;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewBell = itemView.findViewById(R.id.imageView8);
            contentTextView = itemView.findViewById(R.id.notificationContent);
            imageViewChecked = itemView.findViewById(R.id.imageView9);
        }
    }
}
