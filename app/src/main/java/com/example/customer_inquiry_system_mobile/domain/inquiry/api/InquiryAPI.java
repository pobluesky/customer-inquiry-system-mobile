package com.example.customer_inquiry_system_mobile.domain.inquiry.api;

import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquirySummaryResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InquiryAPI {

    @GET("mobile/api/inquiries")
    Call<List<InquirySummaryResponseDTO>> getAllInquiries();

    @GET("mobile/api/inquiries/{id}")
    Call<InquirySummaryResponseDTO> getInquiryById(@Path("id") Long inquiryId);
}

