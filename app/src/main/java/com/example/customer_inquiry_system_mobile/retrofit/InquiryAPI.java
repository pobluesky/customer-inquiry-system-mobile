package com.example.customer_inquiry_system_mobile.retrofit;

import com.example.customer_inquiry_system_mobile.Inquiry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InquiryAPI {

    @GET("mobile/api/inquiries")
    Call<List<Inquiry>> getAllInquiries();

    @GET("mobile/api/inquiries/{id}")
    Call<Inquiry> getInquiryById(@Path("id") Long inquiryId);
}

