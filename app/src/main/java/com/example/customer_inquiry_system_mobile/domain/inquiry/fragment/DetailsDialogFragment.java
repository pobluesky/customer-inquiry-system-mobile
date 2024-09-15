package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DetailsDialogFragment extends DialogFragment {

    private static final String ARG_INQUIRY_ID = "arg_inquiry_id";

    private static final String ARG_NAME = "arg_name";

    private static final String ARG_CUSTOMER_CODE = "arg_customer_code";

    private static final String ARG_CUSTOMER_NAME = "arg_customer_name";

    private static final String ARG_EMAIL = "arg_email";

    private static final String ARG_PHONE = "arg_phone";

    private static final String ARG_COUNTRY = "arg_country";

    private static final String ARG_CORPORATE = "arg_corporate";

    private static final String ARG_SALESPERSON = "arg_salesperson";

    private static final String ARG_INQUIRY_TYPE = "arg_salesperson";

    private static final String ARG_INDUSTRY = "arg_industry";

    private static final String ARG_CORPORATION_CODE = "arg_corporation_code";

    private static final String ARG_PRODUCT_TYPE = "arg_product_type";

    private static final String ARG_PROGRESS = "arg_progress";
    private static final String ARG_CUSTOMER_REQUESTS = "arg_customer_requests";

    private static final String ARG_ADDITIONAL_REQUESTS = "arg_additional_requests";

    private static final String ARG_RESPONSE_DEADLINE = "arg_response_deadline";

    public static DetailsDialogFragment newInstance(
            String inquiryId,
            String name,
            String customerCode,
            String customerName,
            String email,
            String phone,
            String country,
            String corporate,
            String salesPerson,
            String inquiryType,
            String industry,
            String corporationCode,
            String productType,
            String progress,
            String customerRequestDate,
            String additionalRequests,
            String responseDeadline
    ) {
        DetailsDialogFragment fragment = new DetailsDialogFragment();
        Bundle args = new Bundle();

        args.putString(ARG_INQUIRY_ID, inquiryId);
        args.putString(ARG_NAME, name);
        args.putString(ARG_CUSTOMER_CODE, customerCode);
        args.putString(ARG_CUSTOMER_NAME, customerName);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_PHONE, phone);
        args.putString(ARG_COUNTRY, country);
        args.putString(ARG_CORPORATE, corporate);
        args.putString(ARG_SALESPERSON, salesPerson);
        args.putString(ARG_INQUIRY_TYPE, inquiryType);
        args.putString(ARG_INDUSTRY, industry);
        args.putString(ARG_CORPORATION_CODE, corporationCode);
        args.putString(ARG_PRODUCT_TYPE, productType);
        args.putString(ARG_PROGRESS, progress);
        args.putString(ARG_CUSTOMER_REQUESTS, customerRequestDate);
        args.putString(ARG_ADDITIONAL_REQUESTS, additionalRequests);
        args.putString(ARG_ADDITIONAL_REQUESTS, additionalRequests);
        args.putString(ARG_RESPONSE_DEADLINE, responseDeadline);

        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        String inquiryId = getArguments().getString(ARG_INQUIRY_ID);
        String name = getArguments().getString(ARG_NAME);
        String customerCode = getArguments().getString(ARG_CUSTOMER_CODE);
        String customerName = getArguments().getString(ARG_CUSTOMER_NAME);
        String email = getArguments().getString(ARG_EMAIL);
        String phone = getArguments().getString(ARG_PHONE);
        String country = getArguments().getString(ARG_COUNTRY);
        String corporate = getArguments().getString(ARG_CORPORATE);
        String salesPerson = getArguments().getString(ARG_SALESPERSON);
        String inquiryType = getArguments().getString(ARG_INQUIRY_TYPE);
        String industry = getArguments().getString(ARG_INDUSTRY);
        String corporationCode = getArguments().getString(ARG_CORPORATION_CODE);
        String productType = getArguments().getString(ARG_PRODUCT_TYPE);
        String progress = getArguments().getString(ARG_PROGRESS);
        String customerRequest = getArguments().getString(ARG_CUSTOMER_REQUESTS);
        String additionalRequest = getArguments().getString(ARG_ADDITIONAL_REQUESTS);
        String responseDeadline = getArguments().getString(ARG_RESPONSE_DEADLINE);

        builder.setTitle("Inquiry 상세")
                .setMessage(
                                "Inquiry ID: " + inquiryId +
                                "\n고객 이름: " + name +
                                "\n고객사명: " + customerName +
                                "\n고객 코드: " + customerCode +
                                "\n이메일: " + email +
                                "\n연락처: " + phone +
                                "\n국가 : " + country +
                                "\n판매 상사 : " + corporate +
                                "\n판매 계약자 : " + salesPerson +
                                "\n문의 유형 : " + inquiryType +
                                "\n산업 분류 : " + industry +
                                "\n법인 코드: " + corporationCode +
                                "\n제품 유형: " + productType +
                                "\n진행 상황: " + progress +
                                "\n고객사 요청일: " + customerRequest +
                                "\n추가 요청 사항: " + additionalRequest +
                                "\n회신 기한일: " + responseDeadline
                )
                .setPositiveButton("닫기", (dialog, id) -> dialog.dismiss());

        return builder.create();
    }
}
