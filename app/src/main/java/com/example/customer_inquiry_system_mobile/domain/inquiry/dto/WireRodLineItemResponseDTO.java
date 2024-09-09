package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WireRodLineItemResponseDTO extends LineItemResponseDTO {

    @SerializedName("lineItemId")
    @Expose
    private Long lineItemId;

    @SerializedName("inquiryId")
    @Expose
    private Long inquiryId;

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("inqName")
    @Expose
    private String inqName;

    @SerializedName("orderCategory")
    @Expose
    private String orderCategory;

    @SerializedName("diameter")
    @Expose
    private String diameter;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("expectedDeadline")
    @Expose
    private String expectedDeadline;

    @SerializedName("initialQuantity")
    @Expose
    private Integer initialQuantity;

    @SerializedName("customerProcessing")
    @Expose
    private String customerProcessing;

    @SerializedName("finalUse")
    @Expose
    private String finalUse;

    @SerializedName("transportationDestination")
    @Expose
    private String transportationDestination;

    @SerializedName("annualCost")
    @Expose
    private String annualCost;

    @SerializedName("legalRegulatoryReview")
    @Expose
    private String legalRegulatoryReview;

    @SerializedName("legalRegulatoryReviewDetail")
    @Expose
    private String legalRegulatoryReviewDetail;

    @SerializedName("finalCustomer")
    @Expose
    private String finalCustomer;

    public WireRodLineItemResponseDTO() {
    }

    public Long getLineItemId() {
        return lineItemId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public String getKind() {
        return kind;
    }

    public String getInqName() {
        return inqName;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public String getDiameter() {
        return diameter;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getExpectedDeadline() {
        return expectedDeadline;
    }

    public Integer getInitialQuantity() {
        return initialQuantity;
    }

    public String getCustomerProcessing() {
        return customerProcessing;
    }

    public String getFinalUse() {
        return finalUse;
    }

    public String getTransportationDestination() {
        return transportationDestination;
    }

    public String getAnnualCost() {
        return annualCost;
    }

    public String getLegalRegulatoryReview() {
        return legalRegulatoryReview;
    }

    public String getLegalRegulatoryReviewDetail() {
        return legalRegulatoryReviewDetail;
    }

    public String getFinalCustomer() {
        return finalCustomer;
    }
}
