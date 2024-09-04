package com.example.customer_inquiry_system_mobile.domain.inquiry.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquiryResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.fragment.InquiryDetailActivity;
import com.example.customer_inquiry_system_mobile.R;

import java.util.List;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryHolder> {

    private final List<InquiryResponseDTO> inquiryResponseDTOList;

    private final Context context;

    public InquiryAdapter(List<InquiryResponseDTO> inquiryResponseDTOList, Context context) {
        this.inquiryResponseDTOList = inquiryResponseDTOList;
        this.context = context;
    }

    @NonNull
    @Override
    public InquiryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_inquiry_item, parent, false);
        return new InquiryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryHolder holder, int position) {
        InquiryResponseDTO inquiryResponseDTO = inquiryResponseDTOList.get(position);

        holder.inquiryType.setText(inquiryResponseDTO.getInquiryType());
        holder.progress.setText(inquiryResponseDTO.getProgress());
        holder.customerName.setText(inquiryResponseDTO.getCustomerName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, InquiryDetailActivity.class);

            intent.putExtra("inquiry_id", inquiryResponseDTO.getInquiryId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return inquiryResponseDTOList.size();
    }
}
