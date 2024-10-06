package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InquiryResponseDTO {
    @SerializedName("inquiryId")
    @Expose
    private Long inquiryId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("customerCode")
    @Expose
    private String customerCode;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("corporate")
    @Expose
    private String corporate;

    @SerializedName("salesPerson")
    @Expose
    private String salesPerson;

    @SerializedName("inquiryType")
    @Expose
    private String inquiryType;

    @SerializedName("industry")
    @Expose
    private String industry;

    @SerializedName("corporationCode")
    @Expose
    private String corporationCode;

    @SerializedName("productType")
    @Expose
    private String productType;

    @SerializedName("progress")
    @Expose
    private String progress;

    @SerializedName("customerRequestDate")
    @Expose
    private String customerRequestDate;

    @SerializedName("additionalRequests")
    @Expose
    private String additionalRequests;

    @SerializedName("fileName")
    @Expose
    private String fileName;

    @SerializedName("filePath")
    @Expose
    private String filePath;

    @SerializedName("responseDeadline")
    @Expose
    private String responseDeadline;

    @SerializedName("lineItemResponseDTOs")
    @Expose
    private List<LineItemResponseDTO> lineItemResponseDTOs;

    @SerializedName("managerName")
    @Expose
    private String managerName;

    @SerializedName("managerDepartment")
    @Expose
    private String managerDepartment;

    @SerializedName("customInquiryId")
    @Expose
    private String customInquiryId;

    public Long getInquiryId() {
        return inquiryId;
    }

    public String getName() {
        return name;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getCorporate() {
        return corporate;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCorporationCode() {
        return corporationCode;
    }

    public String getProductType() {
        return productType;
    }

    public String getProgress() {
        return progress;
    }

    public String getCustomerRequestDate() {
        return customerRequestDate;
    }

    public String getAdditionalRequests() {
        return additionalRequests;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getResponseDeadline() {
        return responseDeadline;
    }

    public List<LineItemResponseDTO> getLineItemResponseDTOs() {
        return lineItemResponseDTOs;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerDepartment() {
        return managerDepartment;
    }

    public String getCustomInquiryId() {
        return customInquiryId;
    }
}
