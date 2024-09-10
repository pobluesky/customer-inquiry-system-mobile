package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarLineItemResponseDTO extends LineItemResponseDTO{

    @SerializedName("lineItemId")
    @Expose
    private int lineItemId;

    @SerializedName("inquiryId")
    @Expose
    private int inquiryId;

    @SerializedName("lab")
    @Expose
    private String lab;

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("standardOrg")
    @Expose
    private String standardOrg;

    @SerializedName("salesVehicleName")
    @Expose
    private String salesVehicleName;

    @SerializedName("partName")
    @Expose
    private String partName;

    @SerializedName("ixPlate")
    @Expose
    private String ixPlate;

    @SerializedName("thickness")
    @Expose
    private String thickness;

    @SerializedName("width")
    @Expose
    private String width;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("expectedDeliveryDate")
    @Expose
    private String expectedDeliveryDate;

    @SerializedName("transportationDestination")
    @Expose
    private String transportationDestination;

    @SerializedName("orderEdge")
    @Expose
    private String orderEdge;

    @SerializedName("tolerance")
    @Expose
    private String tolerance;

    @SerializedName("annualCost")
    @Expose
    private String annualCost;

    public CarLineItemResponseDTO() {
    }

    public int getLineItemId() {
        return lineItemId;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public String getLab() {
        return lab;
    }

    public String getKind() {
        return kind;
    }

    public String getStandardOrg() {
        return standardOrg;
    }

    public String getSalesVehicleName() {
        return salesVehicleName;
    }

    public String getPartName() {
        return partName;
    }

    public String getIxPlate() {
        return ixPlate;
    }

    public String getThickness() {
        return thickness;
    }

    public String getWidth() {
        return width;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public String getTransportationDestination() {
        return transportationDestination;
    }

    public String getOrderEdge() {
        return orderEdge;
    }

    public String getTolerance() {
        return tolerance;
    }

    public String getAnnualCost() {
        return annualCost;
    }
}
