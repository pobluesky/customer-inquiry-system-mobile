package com.example.customer_inquiry_system_mobile.domain.dashboard.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.Map;
import java.util.List;

public interface DashboardAPI {

    @GET("mobile/api/inquiries/dashboard/average-monthly")
    Call<Map<String, List<Object[]>>> getAverageMonthlyInquiry(
            @Header("Authorization") String token
    );

    @GET("mobile/api/inquiries/dashboard/counts-by-progress")
    Call<Map<String, List<Object[]>>> getInquiryCountsByProgress(
            @Header("Authorization") String token
    );

    @GET("mobile/api/inquiries/dashboard/percentage-completed-uncompleted")
    Call<Map<String, Map<String, String>>> getInquiryPercentageCompletedUncompleted(
            @Header("Authorization") String token
    );

    @GET("mobile/api/inquiries/dashboard/counts-by-productType")
    Call<Map<String, List<Object[]>>> getInquiryCountsByProductType(
            @Header("Authorization") String token
    );
}
