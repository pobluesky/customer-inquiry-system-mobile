package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.customer_inquiry_system_mobile.R;

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
    private static final String ARG_INQUIRY_TYPE = "arg_inquiry_type"; // 수정
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
            String customerRequests,
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
        args.putString(ARG_CUSTOMER_REQUESTS, customerRequests);
        args.putString(ARG_ADDITIONAL_REQUESTS, additionalRequests);
        args.putString(ARG_RESPONSE_DEADLINE, responseDeadline);

        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Arguments 받기
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

        TableLayout tableLayout = new TableLayout(requireActivity());
        tableLayout.setStretchAllColumns(true);
        tableLayout.setBackgroundColor(0xFFFFFFFF);

        addRow(tableLayout, "Inquiry No ", inquiryId);
        addRow(tableLayout, "고객 이름 ", name);
        addRow(tableLayout, "고객사명 ", customerName);
        addRow(tableLayout, "고객 코드 ", customerCode);
        addRow(tableLayout, "이메일 ", email);
        addRow(tableLayout, "연락처 ", phone);
        addRow(tableLayout, "국가 ", country);
        addRow(tableLayout, "판매 상사 ", corporate);
        addRow(tableLayout, "판매 계약자 ", salesPerson);
        addRow(tableLayout, "문의 유형 ", inquiryType);
        addRow(tableLayout, "산업 분류 ", industry);
        addRow(tableLayout, "법인 코드 ", corporationCode);
        addRow(tableLayout, "제품 유형 ", productType);
        addRow(tableLayout, "진행 상태 ", progress);
        addRow(tableLayout, "고객 요청 ", customerRequest);
        addRow(tableLayout, "추가 요청 ", additionalRequest);
        addRow(tableLayout, "응답기한일 ", responseDeadline);

        builder.setView(tableLayout)
                .setPositiveButton("닫기", (dialog, id) -> dialog.dismiss());

        return builder.create();
    }

    // 행 추가 메서드
    private void addRow(TableLayout tableLayout, String label, String value) {
        TableRow row = new TableRow(requireActivity());
        row.setBackgroundResource(R.drawable.table_row_background); // 배경 리소스 설정

        // Label TextView 생성
        TextView labelTextView = new TextView(requireActivity());
        labelTextView.setText(label);
        labelTextView.setPadding(8, 8, 8, 8);
        labelTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1)); // 가중치 설정
        labelTextView.setBackgroundResource(R.drawable.cell_border); // 셀 테두리 설정
        labelTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // Value TextView 생성
        TextView valueTextView = new TextView(requireActivity());
        valueTextView.setText(value);
        valueTextView.setPadding(8, 8, 8, 8);
        valueTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1)); // 가중치 설정
        valueTextView.setBackgroundResource(R.drawable.cell_border); // 셀 테두리 설정
        valueTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // 행에 추가
        row.addView(labelTextView);
        row.addView(valueTextView);

        // 행을 테이블에 추가
        tableLayout.addView(row);
    }

}
