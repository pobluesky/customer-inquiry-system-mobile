package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThickPlateLineItemResponseDTO extends LineItemResponseDTO {

    @SerializedName("lineItemId")
    @Expose
    private Long lineItemId;

    @SerializedName("inquiryId")
    @Expose
    private Long inquiryId;

    @SerializedName("orderPurpose")
    @Expose
    private String orderPurpose;

    @SerializedName("orderInfo")
    @Expose
    private String orderInfo;

    @SerializedName("ladleIngredient")
    @Expose
    private String ladleIngredient;

    @SerializedName("productIngredient")
    @Expose
    private String productIngredient;

    @SerializedName("seal")
    @Expose
    private String seal;

    @SerializedName("grainSizeAnalysis")
    @Expose
    private Boolean grainSizeAnalysis;

    @SerializedName("show")
    @Expose
    private String show;

    @SerializedName("extraShow")
    @Expose
    private String extraShow;

    @SerializedName("agingShow")
    @Expose
    private String agingShow;

    @SerializedName("curve")
    @Expose
    private String curve;

    @SerializedName("additionalRequests")
    @Expose
    private String additionalRequests;

    @SerializedName("hardness")
    @Expose
    private String hardness;

    @SerializedName("dropWeightTest")
    @Expose
    private Boolean dropWeightTest;

    @SerializedName("ultrasonicTransducer")
    @Expose
    private Boolean ultrasonicTransducer;

    public ThickPlateLineItemResponseDTO() {
    }

    public Long getLineItemId() {
        return lineItemId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public String getOrderPurpose() {
        return orderPurpose;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public String getLadleIngredient() {
        return ladleIngredient;
    }

    public String getProductIngredient() {
        return productIngredient;
    }

    public String getSeal() {
        return seal;
    }

    public Boolean getGrainSizeAnalysis() {
        return grainSizeAnalysis;
    }

    public String getShow() {
        return show;
    }

    public String getExtraShow() {
        return extraShow;
    }

    public String getAgingShow() {
        return agingShow;
    }

    public String getCurve() {
        return curve;
    }

    public String getAdditionalRequests() {
        return additionalRequests;
    }

    public String getHardness() {
        return hardness;
    }

    public Boolean getDropWeightTest() {
        return dropWeightTest;
    }

    public Boolean getUltrasonicTransducer() {
        return ultrasonicTransducer;
    }
}
