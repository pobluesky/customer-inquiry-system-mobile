package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.ReviewResponseDTO;
import com.example.customer_inquiry_system_mobile.global.HeaderUtils;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryDetailActivity extends AppCompatActivity {

    private TextView
            inquiryIdDetail,
            inquiryTypeDetail,
            customerNameDetail,
            productTypeDetail;

    private String
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

    private View indicatorDetail, indicatorInquiry, indicatorResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_detail);

        inquiryIdDetail = findViewById(R.id.textViewInquiryId);
        productTypeDetail = findViewById(R.id.productTypeDetail);
        customerNameDetail = findViewById(R.id.customerNameDetail);
        inquiryTypeDetail = findViewById(R.id.inquiryTypeDetail);

        recyclerViewLineItems = findViewById(R.id.recyclerViewLineItems);
        recyclerViewLineItems.setLayoutManager(new LinearLayoutManager(this));

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

        indicatorDetail = findViewById(R.id.indicatorDetail);
        indicatorInquiry = findViewById(R.id.indicatorInquiry);
        indicatorResponse = findViewById(R.id.indicatorResponse);

        LinearLayout sectionInquiry = findViewById(R.id.sectionInquiry);
        LinearLayout sectionResponse = findViewById(R.id.sectionResponse);
        LinearLayout sectionDetail = findViewById(R.id.sectionDetail);

        Button buttonInquiry = findViewById(R.id.buttonInquiry);
        Button buttonDetail = findViewById(R.id.buttonDetail);
        Button buttonResponse = findViewById(R.id.buttonResponse);

        buttonDetail.setSelected(true);

        buttonDetail.setOnClickListener(v -> {
            sectionDetail.setVisibility(View.VISIBLE);
            sectionInquiry.setVisibility(View.GONE);
            sectionResponse.setVisibility(View.GONE);

            Log.d("TAG", "sectionDetail visibility: " + sectionDetail.getVisibility()); // 현재 visibility 확인

            buttonInquiry.setSelected(false);
            buttonDetail.setSelected(true);
            buttonResponse.setSelected(false);

            updateIndicator(indicatorDetail);
        });

        buttonInquiry.setOnClickListener(v -> {
            sectionInquiry.setVisibility(View.VISIBLE);
            sectionResponse.setVisibility(View.GONE);
            sectionDetail.setVisibility(View.GONE);

            buttonInquiry.setSelected(true);
            buttonDetail.setSelected(false);
            buttonResponse.setSelected(false);

            updateIndicator(indicatorInquiry);
        });

        buttonResponse.setOnClickListener(v -> {
            sectionInquiry.setVisibility(View.GONE);
            sectionResponse.setVisibility(View.VISIBLE);
            sectionDetail.setVisibility(View.GONE);

            buttonInquiry.setSelected(false);
            buttonDetail.setSelected(false);
            buttonResponse.setSelected(true);

            fetchReviewById(inquiryId);
            updateIndicator(indicatorResponse);
        });

        sectionInquiry.setVisibility(View.GONE);
        sectionDetail.setVisibility(View.VISIBLE);
        sectionResponse.setVisibility(View.GONE);

        ImageView revertIcon = findViewById(R.id.revertIcon);
        revertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateIndicator(View selectedIndicator) {
        // 모든 작은 네모를 기본 색상으로 초기화
        indicatorDetail.setBackgroundColor(Color.WHITE);
        indicatorInquiry.setBackgroundColor(Color.WHITE);
        indicatorResponse.setBackgroundColor(Color.WHITE);

        // 선택된 네모의 색상 변경
        selectedIndicator.setBackgroundColor(Color.parseColor("#B0CBFF")); // 선택된 색상
    }

    private void setDetails() {
        TextView textViewCustomerName = findViewById(R.id.customerName);
        TextView textViewCustomerCode = findViewById(R.id.customerCode);
        TextView textViewName = findViewById(R.id.name);
        TextView textViewEmail = findViewById(R.id.email);
        TextView textViewPhone = findViewById(R.id.phone);
        TextView textViewCountry = findViewById(R.id.country);
        TextView textViewCorporate = findViewById(R.id.corporate);
        TextView textViewSalesPerson = findViewById(R.id.salesPerson);
        TextView textViewInquiryType = findViewById(R.id.inquiryType);
        TextView textViewIndustry = findViewById(R.id.industry);
        TextView textViewCorporationCode = findViewById(R.id.corporationCode);
        TextView textViewProductType = findViewById(R.id.productType);
        TextView textViewProgress = findViewById(R.id.progress);
        TextView textViewCustomerRequestDate = findViewById(R.id.customerRequestDate);
        TextView textViewAdditionalRequests = findViewById(R.id.additionalRequests);
        TextView textViewResponseDeadline = findViewById(R.id.responseDeadline);

        textViewCustomerName.setText(customerName);
        textViewCustomerCode.setText(customerCode);
        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewProductType.setText(productType);
        textViewProgress.setText(progress);
        textViewCustomerRequestDate.setText(customerRequestDate);
        textViewAdditionalRequests.setText(additionalRequests);
        textViewResponseDeadline.setText(responseDeadline);
        textViewPhone.setText(phone);
        textViewCountry.setText(country);
        textViewCorporate.setText(corporate);
        textViewSalesPerson.setText(salesPerson);
        textViewInquiryType.setText(inquiryType);
        textViewIndustry.setText(industry);
        textViewCorporationCode.setText(corporationCode);
    }

    private void fetchInquiryById(Long inquiryId, String productType) {
        RetrofitService retrofitService = new RetrofitService(productType);
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        inquiryAPI.getInquiryById(inquiryId).enqueue(new Callback<InquiryResponseDTO>() {
            @Override
            public void onResponse(
                    @NonNull Call<InquiryResponseDTO> call,
                    @NonNull Response<InquiryResponseDTO> response
            ) {
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
                    Log.e(
                            "API_RESPONSE_ERROR",
                            "서버 응답 실패: " + response.code() +
                                    ", 메시지: " + response.message()
                    );
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

    private void fetchReviewById(Long inquiryId) {
        RetrofitService retrofitService = new RetrofitService(null);
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        inquiryAPI.getReviewById(inquiryId).enqueue(new Callback<ReviewResponseDTO>() {
            @Override
            public void onResponse(
                    @NonNull Call<ReviewResponseDTO> call,
                    @NonNull Response<ReviewResponseDTO> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "서버 응답 성공: " + response.body().toString());
                    updateReviewUI(response.body());
                } else {
                    try {
                        JSONObject errorBody = new JSONObject(response.errorBody().string());
                        String code = errorBody.getString("code");
                        if (code.equals("R0001")) {
                            TextView firstReviewTextView = findViewById(R.id.firstReview);
                            firstReviewTextView.setText("검토 대기중");
                            firstReviewTextView.setGravity(Gravity.CENTER);
                            firstReviewTextView
                                    .setBackgroundResource(R.drawable.review_pending_background);
                            firstReviewTextView
                                    .setTextColor(getResources().getColor(android.R.color.black));

                            ViewGroup.LayoutParams firstReviewParams = firstReviewTextView.getLayoutParams();
                            firstReviewParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
                            firstReviewParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                            firstReviewTextView.setLayoutParams(firstReviewParams);

                            TextView finalReviewTextView = findViewById(R.id.finalReview);
                            finalReviewTextView.setText("검토 대기중");
                            finalReviewTextView.setGravity(Gravity.CENTER);
                            finalReviewTextView
                                    .setBackgroundResource(R.drawable.review_pending_background);
                            finalReviewTextView
                                    .setTextColor(getResources().getColor(android.R.color.black));

                            ViewGroup.LayoutParams finalReviewParams = finalReviewTextView.getLayoutParams();
                            finalReviewParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
                            finalReviewParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                            finalReviewTextView.setLayoutParams(finalReviewParams);
                        } else {
                            Log.e(
                                    "API_RESPONSE_ERROR",
                                    "서버 응답 실패: " + response.code() +
                                            ", 메시지: " + response.message()
                            );
                        }
                    } catch (Exception e) {
                        Log.e(
                                "API_RESPONSE_ERROR",
                                "JSON 파싱 에러: " + e.getMessage(),
                                e
                        );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponseDTO> call, @NonNull Throwable t) {
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

        setDetails();
    }

    private void updateReviewUI(ReviewResponseDTO reviewResponseDTO) {
        TextView firstReviewTextView = findViewById(R.id.firstReview);
        TextView finalReviewTextView = findViewById(R.id.finalReview);

            firstReviewTextView.setText(reviewResponseDTO.getReviewText());
            firstReviewTextView.setBackgroundResource(0);
            firstReviewTextView.setTextColor(getResources().getColor(android.R.color.black));

            finalReviewTextView.setText(reviewResponseDTO.getFinalReview());
            finalReviewTextView.setBackgroundResource(0);
            finalReviewTextView.setTextColor(getResources().getColor(android.R.color.black));
    }
}
