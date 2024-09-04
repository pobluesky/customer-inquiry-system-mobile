package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquiryResponseDTO;
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

        inquiryAPI.getInquiryById(inquiryId).enqueue(new Callback<InquiryResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<InquiryResponseDTO> call, @NonNull Response<InquiryResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InquiryResponseDTO inquiryResponseDTO = response.body();
                    // 데이터를 뷰에 설정
                    inquiryType.setText(inquiryResponseDTO.getInquiryType());
                    progress.setText(inquiryResponseDTO.getProgress());
                    customerName.setText(inquiryResponseDTO.getCustomerName());
                } else {
                    Toast.makeText(
                            InquiryDetailActivity.this,
                            "데이터를 가져오는 데 실패했습니다.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InquiryResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(
                        InquiryDetailActivity.this,
                        "API 호출 실패: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
