package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.inquiry.adapter.HeaderPagerAdapter;
import com.example.customer_inquiry_system_mobile.domain.inquiry.adapter.LineItemAdapter;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquiryResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.api.InquiryAPI;
import com.example.customer_inquiry_system_mobile.global.HeaderUtils;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryDetailActivity extends AppCompatActivity {

    private TextView
            inquiryIdDetail,
            inquiryTypeDetail,
            customerNameDetail,
            productTypeDetail,
            corporateDetail,
            countryDetail,
            industryDetail;

    private String
            inquiryId,
            name,
            customerName,
            customerCode,
            email,
            phone,
            country,
            corporate,
            salesPerson,
            inquiryType,
            industry,
            corporationCode,
            productType,
            progress,
            customerRequestDate,
            additionalRequests,
            responseDeadline;

    private RecyclerView recyclerViewLineItems;

    private LineItemAdapter lineItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_detail);

        inquiryIdDetail = findViewById(R.id.textViewInquiryId);
        productTypeDetail = findViewById(R.id.productTypeDetail);
        customerNameDetail = findViewById(R.id.customerNameDetail);
        countryDetail = findViewById(R.id.countryDetail);
        inquiryTypeDetail = findViewById(R.id.inquiryTypeDetail);
        corporateDetail = findViewById(R.id.corporateDetail);
        industryDetail = findViewById(R.id.industryDetail);

        recyclerViewLineItems = findViewById(R.id.recyclerViewLineItems);
        recyclerViewLineItems.setLayoutManager(new LinearLayoutManager(this));

        ImageView seeMoreIcon = findViewById(R.id.seeMoreIcon);

        seeMoreIcon.setOnClickListener(v -> {
            DetailsDialogFragment dialogFragment = DetailsDialogFragment.newInstance(
                    inquiryId,
                    name,
                    customerCode,
                    customerName,
                    email,
                    phone,
                    country,
                    corporate,
                    salesPerson,
                    inquiryType,
                    industry,
                    corporationCode,
                    productType,
                    progress,
                    customerRequestDate,
                    additionalRequests,
                    responseDeadline
            );
            dialogFragment.show(getSupportFragmentManager(), "detailsDialog");
        });

        String productType = getIntent().getStringExtra("product_type");
        if (productType != null) {
            ViewPager2 viewPagerHeaders = findViewById(R.id.viewPagerHeaders);
            List<Integer> headerLayouts = HeaderUtils.getHeaderLayouts(productType);

            HeaderPagerAdapter headerAdapter = new HeaderPagerAdapter(headerLayouts);
            viewPagerHeaders.setAdapter(headerAdapter);
        }

        Long inquiryId = getIntent().getLongExtra("inquiry_id", -1);

        if (inquiryId != -1) {
            fetchInquiryById(inquiryId, productType);
        } else {
            Toast.makeText(
                    this,
                    "유효하지 않은 문의 ID입니다.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        ImageView revertIcon = findViewById(R.id.revertIcon);
        revertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetchInquiryById(Long inquiryId, String productType) {
        RetrofitService retrofitService = new RetrofitService(productType);
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        inquiryAPI.getInquiryById(inquiryId).enqueue(new Callback<InquiryResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<InquiryResponseDTO> call, @NonNull Response<InquiryResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "서버 응답 성공: " + response.body().toString());

                    InquiryResponseDTO inquiryResponseDTO = response.body();
                    String productType = inquiryResponseDTO.getProductType();

                    lineItemAdapter = new LineItemAdapter(
                            inquiryResponseDTO.getLineItemResponseDTOs(),
                            productType
                    );

                    recyclerViewLineItems.setAdapter(lineItemAdapter);
                    updateUI(inquiryResponseDTO);
                } else {
                    Log.e("API_RESPONSE_ERROR", "서버 응답 실패: " + response.code() + ", 메시지: " + response.message());
                }
            }


            @Override
            public void onFailure(@NonNull Call<InquiryResponseDTO> call, @NonNull Throwable t) {
                Log.e("API_CALL_FAILURE", "API 호출 실패: " + t.getMessage(), t);
                Toast.makeText(
                        InquiryDetailActivity.this,
                        "API 호출 실패: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void updateUI(InquiryResponseDTO inquiryResponseDTO) {
        inquiryIdDetail.setText(String.valueOf(inquiryResponseDTO.getInquiryId()));
                inquiryTypeDetail.setText(inquiryResponseDTO.getInquiryType());
                customerNameDetail.setText(inquiryResponseDTO.getCustomerName());
                productTypeDetail.setText(inquiryResponseDTO.getProductType());
                corporateDetail.setText(inquiryResponseDTO.getCorporationCode());
                countryDetail.setText(inquiryResponseDTO.getCountry());
                industryDetail.setText(inquiryResponseDTO.getIndustry());

        inquiryId = String.valueOf(inquiryResponseDTO.getInquiryId());
        name = inquiryResponseDTO.getName();
        customerName = inquiryResponseDTO.getCustomerName();
        customerCode = inquiryResponseDTO.getCustomerCode();
        email = inquiryResponseDTO.getEmail();
        phone = inquiryResponseDTO.getPhone();
        country = inquiryResponseDTO.getCountry();
        corporate = inquiryResponseDTO.getCorporate();
        salesPerson = inquiryResponseDTO.getSalesPerson();
        inquiryType = inquiryResponseDTO.getInquiryType();
        industry = inquiryResponseDTO.getIndustry();
        corporationCode = inquiryResponseDTO.getCorporationCode();
        productType = inquiryResponseDTO.getProductType();
        progress = inquiryResponseDTO.getProgress();
        customerRequestDate = inquiryResponseDTO.getCustomerRequestDate();
        additionalRequests = inquiryResponseDTO.getAdditionalRequests();
        responseDeadline = inquiryResponseDTO.getResponseDeadline();
    }
}
