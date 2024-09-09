package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColdRolledLineItemResponseDTO extends LineItemResponseDTO {

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

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("expectedDeadline")
    @Expose
    private String expectedDeadline;

    @SerializedName("orderEdge")
    @Expose
    private String orderEdge;

    @SerializedName("inDiameter")
    @Expose
    private String inDiameter;

    @SerializedName("outDiameter")
    @Expose
    private String outDiameter;

    @SerializedName("sleeveThickness")
    @Expose
    private String sleeveThickness;

    @SerializedName("tensileStrength")
    @Expose
    private String tensileStrength;

    @SerializedName("elongationRatio")
    @Expose
    private String elongationRatio;

    @SerializedName("hardness")
    @Expose
    private String hardness;

    public ColdRolledLineItemResponseDTO() {
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

    public Integer getQuantity() {
        return quantity;
    }

    public String getExpectedDeadline() {
        return expectedDeadline;
    }

    public String getOrderEdge() {
        return orderEdge;
    }

    public String getInDiameter() {
        return inDiameter;
    }

    public String getOutDiameter() {
        return outDiameter;
    }

    public String getSleeveThickness() {
        return sleeveThickness;
    }

    public String getTensileStrength() {
        return tensileStrength;
    }

    public String getElongationRatio() {
        return elongationRatio;
    }

    public String getHardness() {
        return hardness;
    }
}
