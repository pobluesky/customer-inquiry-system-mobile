package com.example.customer_inquiry_system_mobile.domain.inquiry.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;

public class InquiryHolder extends RecyclerView.ViewHolder {

    TextView inquiryType, progress ,customerName;

    public InquiryHolder(@NonNull View itemView){
        super(itemView);
        inquiryType=itemView.findViewById(R.id.inquiryListItem_InquiryType);
        progress=itemView.findViewById(R.id.inquiryListItem_Progress);
        customerName=itemView.findViewById(R.id.inquiryListItem_CustomerName);

    }
}