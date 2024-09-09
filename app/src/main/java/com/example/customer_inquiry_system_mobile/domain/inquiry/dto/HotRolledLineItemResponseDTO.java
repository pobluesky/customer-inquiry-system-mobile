package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotRolledLineItemResponseDTO extends LineItemResponseDTO {

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

    @SerializedName("thickness")
    @Expose
    private String thickness;

    @SerializedName("width")
    @Expose
    private String width;

    @SerializedName("hardness")
    @Expose
    private String hardness;

    @SerializedName("flatness")
    @Expose
    private String flatness;

    @SerializedName("orderEdge")
    @Expose
    private String orderEdge;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("yieldingPoint")
    @Expose
    private String yieldingPoint;

    @SerializedName("tensileStrength")
    @Expose
    private String tensileStrength;

    @SerializedName("elongationRatio")
    @Expose
    private String elongationRatio;

    @SerializedName("camber")
    @Expose
    private String camber;

    @SerializedName("annualCost")
    @Expose
    private String annualCost;

    public HotRolledLineItemResponseDTO() {
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

    public String getThickness() {
        return thickness;
    }

    public String getWidth() {
        return width;
    }

    public String getHardness() {
        return hardness;
    }

    public String getFlatness() {
        return flatness;
    }

    public String getOrderEdge() {
        return orderEdge;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getYieldingPoint() {
        return yieldingPoint;
    }

    public String getTensileStrength() {
        return tensileStrength;
    }

    public String getElongationRatio() {
        return elongationRatio;
    }

    public String getCamber() {
        return camber;
    }

    public String getAnnualCost() {
        return annualCost;
    }
}
