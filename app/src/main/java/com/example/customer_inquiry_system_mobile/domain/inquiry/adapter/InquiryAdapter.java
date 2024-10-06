package com.example.customer_inquiry_system_mobile.domain.inquiry.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
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

        holder.customInquiryId.setText(inquiryResponseDTO.getCustomInquiryId());
        holder.inquiryType.setText(inquiryResponseDTO.getInquiryType());
        holder.progress.setText(inquiryResponseDTO.getProgress());
        holder.customerName.setText(inquiryResponseDTO.getCustomerName());

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // 사각형 모양
        drawable.setCornerRadius(70); // 모서리 반경 설정

        if ("견적문의".equals(inquiryResponseDTO.getInquiryType())) {
            drawable.setColor(Color.parseColor("#F8EDDB"));
        } else if ("품질/견적문의".equals(inquiryResponseDTO.getInquiryType())) {
            drawable.setColor(Color.parseColor("#C4DEDA"));
        } else {
            drawable.setColor(Color.TRANSPARENT); // 투명색 설정
        }

        holder.inquiryType.setBackground(drawable); // 버튼에 설정

        GradientDrawable progressDrawable = new GradientDrawable();
        progressDrawable.setShape(GradientDrawable.RECTANGLE);
        progressDrawable.setCornerRadius(70);
        progressDrawable.setColor(Color.parseColor("#03507D")); // 배경색 설정

        // TextView에 배경 설정
        holder.progress.setBackground(progressDrawable);
        holder.progress.setTextColor(Color.parseColor("#FFFFFF")); // 글자색 설정

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, InquiryDetailActivity.class);

            intent.putExtra("inquiry_id", inquiryResponseDTO.getInquiryId());
            intent.putExtra("name", inquiryResponseDTO.getName());
            intent.putExtra("customer_name", inquiryResponseDTO.getCustomerName());
            intent.putExtra("customer_code", inquiryResponseDTO.getCustomerCode());
            intent.putExtra("email", inquiryResponseDTO.getEmail());
            intent.putExtra("phone", inquiryResponseDTO.getPhone());
            intent.putExtra("country", inquiryResponseDTO.getCountry());
            intent.putExtra("corporate", inquiryResponseDTO.getCorporate());
            intent.putExtra("sales_person", inquiryResponseDTO.getSalesPerson());
            intent.putExtra("inquiry_type", inquiryResponseDTO.getInquiryType());
            intent.putExtra("industry", inquiryResponseDTO.getIndustry());
            intent.putExtra("corporation_code", inquiryResponseDTO.getCorporationCode());
            intent.putExtra("product_type", inquiryResponseDTO.getProductType());
            intent.putExtra("progress", inquiryResponseDTO.getProgress());
            intent.putExtra("customer_request_date", inquiryResponseDTO.getCustomerRequestDate());
            intent.putExtra("additional_requests", inquiryResponseDTO.getAdditionalRequests());
            intent.putExtra("file_name", inquiryResponseDTO.getFileName());
            intent.putExtra("file_path", inquiryResponseDTO.getFilePath());
            intent.putExtra("response_deadline", inquiryResponseDTO.getResponseDeadline());
            intent.putExtra("custom_inquiry_id", inquiryResponseDTO.getCustomInquiryId());

            String salesManagerName = inquiryResponseDTO.getManagerName();
            String salesManagerDepartment = inquiryResponseDTO.getManagerDepartment();

            if (salesManagerName != null) {
                intent.putExtra("sales_manager_name", salesManagerName);
                intent.putExtra("sales_manager_department", salesManagerDepartment);
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return inquiryResponseDTOList.size();
    }
}
