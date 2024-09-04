package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquirySummaryResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.api.InquiryAPI;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryDetailActivity extends AppCompatActivity {

    private TextView inquiryType, progress, customerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_detail);

        // 뷰 초기화
        inquiryType = findViewById(R.id.inquiryTypeDetail);
        progress = findViewById(R.id.progressDetail);
        customerName = findViewById(R.id.customerNameDetail);

        Long inquiryId = getIntent().getLongExtra("inquiry_id", -1);

        if (inquiryId != -1) {
            fetchInquiryById(inquiryId);
        } else {
            Toast.makeText(
                    this,
                    "유효하지 않은 문의 ID입니다.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void fetchInquiryById(Long inquiryId) {
        RetrofitService retrofitService = new RetrofitService();
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        inquiryAPI.getInquiryById(inquiryId).enqueue(new Callback<InquirySummaryResponseDTO>() {
            @Override
            public void onResponse(
                    @NonNull Call<InquirySummaryResponseDTO> call,
                    @NonNull Response<InquirySummaryResponseDTO> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    InquirySummaryResponseDTO inquirySummaryResponseDTO = response.body();
                    // 데이터를 뷰에 설정
                    inquiryType.setText(inquirySummaryResponseDTO.getInquiryType());
                    progress.setText(inquirySummaryResponseDTO.getProgress());
                    customerName.setText(inquirySummaryResponseDTO.getCustomerName());
                } else {
                    Toast.makeText(
                            InquiryDetailActivity.this,
                            "데이터를 가져오는 데 실패했습니다.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InquirySummaryResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(
                        InquiryDetailActivity.this,
                        "API 호출 실패: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
