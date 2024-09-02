package com.example.customer_inquiry_system_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inquiry {
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
