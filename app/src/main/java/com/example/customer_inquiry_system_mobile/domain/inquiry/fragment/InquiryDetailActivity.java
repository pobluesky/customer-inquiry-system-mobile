package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.inquiry.adapter.LineItemAdapter;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquiryResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.api.InquiryAPI;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.LineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryDetailActivity extends AppCompatActivity {

    private TextView
            inquiryIdDetail,
            nameDetail,
            customerNameDetail,
            customerCodeDetail,
            emailDetail,
            phoneDetail,
            countryDetail,
            corporateDetail,
            salesPersonDetail,
            inquiryTypeDetail,
            industryDetail,
            corporationCodeDetail,
            productTypeDetail,
            progressDetail,
            customerRequestDateDetail,
            additionalRequestsDetail,
            fileNameDetail,
            responseDeadlineDetail;

    private RecyclerView recyclerViewLineItems;

    private LineItemAdapter lineItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_detail);

        inquiryIdDetail = findViewById(R.id.inquiryIdDetail);
        nameDetail = findViewById(R.id.nameDetail);
        customerNameDetail = findViewById(R.id.customerNameDetail);
        customerCodeDetail = findViewById(R.id.customerCodeDetail);
        emailDetail = findViewById(R.id.emailDetail);
        phoneDetail = findViewById(R.id.phoneDetail);
        countryDetail = findViewById(R.id.countryDetail);
        corporateDetail = findViewById(R.id.corporateDetail);
        salesPersonDetail = findViewById(R.id.salesPersonDetail);
        inquiryTypeDetail = findViewById(R.id.inquiryTypeDetail);
        industryDetail = findViewById(R.id.industryDetail);
        corporationCodeDetail = findViewById(R.id.corporationCodeDetail);
        productTypeDetail = findViewById(R.id.productTypeDetail);
        progressDetail = findViewById(R.id.progressDetail);
        customerRequestDateDetail = findViewById(R.id.customerRequestDateDetail);
        additionalRequestsDetail = findViewById(R.id.additionalRequestsDetail);
        fileNameDetail = findViewById(R.id.fileNameDetail);
        responseDeadlineDetail = findViewById(R.id.responseDeadlineDetail);

        recyclerViewLineItems = findViewById(R.id.recyclerViewLineItems);
        recyclerViewLineItems.setLayoutManager(new LinearLayoutManager(this));

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
            public void onResponse(
                    @NonNull Call<InquiryResponseDTO> call,
                    @NonNull Response<InquiryResponseDTO> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    InquiryResponseDTO inquiryResponseDTO = response.body();

                    inquiryIdDetail.setText(String.valueOf(inquiryResponseDTO.getInquiryId()));
                    nameDetail.setText(inquiryResponseDTO.getName());
                    customerNameDetail.setText(inquiryResponseDTO.getCustomerName());
                    customerCodeDetail.setText(inquiryResponseDTO.getCustomerCode());
                    emailDetail.setText(inquiryResponseDTO.getEmail());
                    phoneDetail.setText(inquiryResponseDTO.getPhone());
                    countryDetail.setText(inquiryResponseDTO.getCountry());
                    corporateDetail.setText(inquiryResponseDTO.getCorporate());
                    salesPersonDetail.setText(inquiryResponseDTO.getSalesPerson());
                    inquiryTypeDetail.setText(inquiryResponseDTO.getInquiryType());
                    industryDetail.setText(inquiryResponseDTO.getIndustry());
                    corporationCodeDetail.setText(inquiryResponseDTO.getCorporationCode());
                    productTypeDetail.setText(inquiryResponseDTO.getProductType());
                    progressDetail.setText(inquiryResponseDTO.getProgress());
                    customerRequestDateDetail.setText(inquiryResponseDTO.getCustomerRequestDate());
                    additionalRequestsDetail.setText(inquiryResponseDTO.getAdditionalRequests());
                    fileNameDetail.setText(inquiryResponseDTO.getFileName());
                    responseDeadlineDetail.setText(inquiryResponseDTO.getResponseDeadline());

                    List<LineItemResponseDTO> lineItems = inquiryResponseDTO.getLineItemResponseDTOs();
                    lineItemAdapter = new LineItemAdapter(lineItems);
                    recyclerViewLineItems.setAdapter(lineItemAdapter);
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
