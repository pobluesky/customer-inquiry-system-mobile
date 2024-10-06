package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.customer_inquiry_system_mobile.domain.notification.NotificationActivity;
import com.example.customer_inquiry_system_mobile.global.HeaderUtils;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryDetailActivity extends AppCompatActivity {

    private TextView
            inquiryIdDetail;

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

    // Layout 내의 Button 및 TextView 참조
    Button inquiryTypeButton;
    TextView progressTextView;
    TextView customerNameTextView;
    TextView firstReviewLabelTextView;
    TextView finalReviewLabelTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_detail);

        inquiryIdDetail = findViewById(R.id.textViewInquiryId);

        ImageView bellIcon = findViewById(R.id.bell);

        bellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        InquiryDetailActivity.this,
                        NotificationActivity.class
                );

                startActivity(intent);
            }
        });

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
            Log.e(
                    "InquiryDetailActivity",
                    "유효하지 않은 문의 id 입니다."
            );
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

        inquiryTypeButton = findViewById(R.id.inquiryListItem_InquiryType);
        progressTextView = findViewById(R.id.inquiryListItem_Progress);
        customerNameTextView = findViewById(R.id.inquiryListItem_CustomerName);
        firstReviewLabelTextView = findViewById(R.id.firstReviewLabel);
        finalReviewLabelTextView = findViewById(R.id.finalReviewLabel);

        // Progress 배경 설정
        GradientDrawable progressDrawable = new GradientDrawable();
        progressDrawable.setShape(GradientDrawable.RECTANGLE);
        progressDrawable.setCornerRadius(70);
        progressDrawable.setColor(Color.parseColor("#03507D"));
        progressTextView.setBackground(progressDrawable);
        progressTextView.setTextColor(Color.parseColor("#FFFFFF"));

        GradientDrawable firstReviewDrawable = new GradientDrawable();
        firstReviewDrawable.setShape(GradientDrawable.RECTANGLE);
        firstReviewDrawable.setCornerRadius(70);
        firstReviewDrawable.setColor(Color.parseColor("#03507D"));
        firstReviewLabelTextView.setBackground(progressDrawable);
        firstReviewLabelTextView.setTextColor(Color.parseColor("#FFFFFF"));

        GradientDrawable finalReviewDrawable = new GradientDrawable();
        finalReviewDrawable.setShape(GradientDrawable.RECTANGLE);
        finalReviewDrawable.setCornerRadius(70);
        finalReviewDrawable.setColor(Color.parseColor("#03507D"));
        finalReviewLabelTextView.setBackground(progressDrawable);
        finalReviewLabelTextView.setTextColor(Color.parseColor("#FFFFFF"));

    }

    private void updateIndicator(View selectedIndicator) {
        indicatorDetail.setBackgroundColor(Color.WHITE);
        indicatorInquiry.setBackgroundColor(Color.WHITE);
        indicatorResponse.setBackgroundColor(Color.WHITE);

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
        TextView textViewProductTypeText = findViewById(R.id.productTypeText);
        TextView textViewProgress = findViewById(R.id.progress);
        TextView textViewCustomerRequestDate = findViewById(R.id.customerRequestDate);
        TextView textViewAdditionalRequests = findViewById(R.id.additionalRequests);
        TextView textViewResponseDeadline = findViewById(R.id.responseDeadline);

        textViewCustomerName.setText(customerName);
        textViewCustomerCode.setText(customerCode);
        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewProductType.setText(productType);
        textViewProductTypeText.setText(productType);
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
                Log.e(
                        "InquiryDetailActivity",
                        "API 호출 실패: " + t.getMessage()
                );
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
                Log.e(
                        "InquiryDetailActivity",
                        "API 호출 실패: " + t.getMessage()
                );
            }
        });
    }


    private void updateUI(InquiryResponseDTO inquiryResponseDTO) {
        inquiryIdDetail.setText(inquiryResponseDTO.getCustomInquiryId());
        inquiryTypeButton.setText(inquiryResponseDTO.getInquiryType());
        progressTextView.setText(inquiryResponseDTO.getProgress());
        customerNameTextView.setText(inquiryResponseDTO.getCustomerName());

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

        setInquiryTypeButton();

        TextView managerNameTextView = findViewById(R.id.managerName);
        TextView managerDepartmentTextView = findViewById(R.id.managerDepartment);

        String salesManagerName = inquiryResponseDTO.getManagerName();
        String salesManagerDepartment = inquiryResponseDTO.getManagerDepartment();

        if (salesManagerName != null) {
            managerNameTextView.setText(salesManagerName);
            managerDepartmentTextView.setText(salesManagerDepartment);
        } else {
            managerNameTextView.setText("담당자 배정 전 입니다.");
            managerDepartmentTextView.setText("N/A");
        }

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

    private void setInquiryTypeButton() {
        Log.d("InquiryType", "Setting inquiry type: " + inquiryType);

        GradientDrawable inquiryTypeDrawable = new GradientDrawable();
        inquiryTypeDrawable.setShape(GradientDrawable.RECTANGLE); // 사각형 모양
        inquiryTypeDrawable.setCornerRadius(70); // 모서리 반경 설정

        if ("견적문의".equals(inquiryType)) {
            inquiryTypeDrawable.setColor(Color.parseColor("#F8EDDB"));
        } else if ("품질/견적문의".equals(inquiryType)) {
            inquiryTypeDrawable.setColor(Color.parseColor("#C4DEDA"));
        } else {
            inquiryTypeDrawable.setColor(Color.TRANSPARENT); // 투명색 설정
        }

        inquiryTypeButton.setBackground(inquiryTypeDrawable); // Button 배경 설정
        inquiryTypeButton.setText(inquiryType);
    }
}
