package com.example.customer_inquiry_system_mobile.domain.inquiry.api;

import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquiryResponseDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface InquiryAPI {

    @GET("mobile/api/inquiries")
    Call<List<InquiryResponseDTO>> getAllInquiries();

    @GET("mobile/api/inquiries/{id}")
    Call<InquiryResponseDTO> getInquiryById(@Path("id") Long inquiryId);

    @GET("mobile/api/inquiries/search")
    Call<List<InquiryResponseDTO>> getInquiriesBySearch(
            @Query("sortBy") String sortBy,
            @QueryMap Map<String, String> options
    );
}

