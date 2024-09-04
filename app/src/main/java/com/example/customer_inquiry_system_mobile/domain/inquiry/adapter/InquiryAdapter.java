package com.example.customer_inquiry_system_mobile.domain.inquiry.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquirySummaryResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.fragment.InquiryDetailActivity;
import com.example.customer_inquiry_system_mobile.R;

import java.util.List;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryHolder> {

    private final List<InquirySummaryResponseDTO> inquirySummaryResponseDTOList;

    private final Context context;

    public InquiryAdapter(List<InquirySummaryResponseDTO> inquirySummaryResponseDTOList, Context context) {
        this.inquirySummaryResponseDTOList = inquirySummaryResponseDTOList;
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
        InquirySummaryResponseDTO inquirySummaryResponseDTO = inquirySummaryResponseDTOList.get(position);

        holder.inquiryType.setText(inquirySummaryResponseDTO.getInquiryType());
        holder.progress.setText(inquirySummaryResponseDTO.getProgress());
        holder.customerName.setText(inquirySummaryResponseDTO.getCustomerName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, InquiryDetailActivity.class);

            intent.putExtra("inquiry_id", inquirySummaryResponseDTO.getInquiryId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return inquirySummaryResponseDTOList.size();
    }
}
