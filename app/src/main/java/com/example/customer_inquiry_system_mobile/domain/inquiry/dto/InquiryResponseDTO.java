package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InquiryResponseDTO {
    @SerializedName("inquiryId")
    @Expose
    Long inquiryId;

    @SerializedName("progress")
    @Expose
    String progress;

    @SerializedName("inquiryType")
    @Expose
    String inquiryType;

    @SerializedName("customerName")
    @Expose
    String customerName;

    public Long getInquiryId() {
        return inquiryId;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProgress() {
        return progress;
    }
}
