package com.example.customer_inquiry_system_mobile.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;

public class QuestionHolder extends RecyclerView.ViewHolder {

    TextView title ,status, type;

    public QuestionHolder(@NonNull View itemView){
        super(itemView);
        title=itemView.findViewById(R.id.questionListItem_title);
        status=itemView.findViewById(R.id.questionListItem_status);
        type=itemView.findViewById(R.id.questionListItem_type);
    }
}