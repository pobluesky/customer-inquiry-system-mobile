package com.example.customer_inquiry_system_mobile.domain.inquiry.api;

import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquiryResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InquiryAPI {

    @GET("mobile/api/inquiries")
    Call<List<InquiryResponseDTO>> getAllInquiries();

    @GET("mobile/api/inquiries/{id}")
    Call<InquiryResponseDTO> getInquiryById(@Path("id") Long inquiryId);

    @GET("/search")
    Call<List<InquiryResponseDTO>> getInquiriesBySearch(
            @Query("sortBy") String sortBy,
            @Query("progress") String progress,
            @Query("productType") String productType,
            @Query("customerName") String customerName,
            @Query("inquiryType") String inquiryType,
            @Query("salesPerson") String salesPerson,
            @Query("industry") String industry,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("salesManagerName") String salesManagerName,
            @Query("qualityManagerName") String qualityManagerName
    );
}

