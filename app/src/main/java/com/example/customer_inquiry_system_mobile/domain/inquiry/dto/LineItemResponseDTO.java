package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineItemResponseDTO {

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

    @SerializedName("edge")
    @Expose
    private String edge;

    @SerializedName("tolerance")
    @Expose
    private String tolerance;

    @SerializedName("annualCost")
    @Expose
    private String annualCost;

    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getStandardOrg() {
        return standardOrg;
    }

    public void setStandardOrg(String standardOrg) {
        this.standardOrg = standardOrg;
    }

    public String getSalesVehicleName() {
        return salesVehicleName;
    }

    public void setSalesVehicleName(String salesVehicleName) {
        this.salesVehicleName = salesVehicleName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getIxPlate() {
        return ixPlate;
    }

    public void setIxPlate(String ixPlate) {
        this.ixPlate = ixPlate;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getTransportationDestination() {
        return transportationDestination;
    }

    public void setTransportationDestination(String transportationDestination) {
        this.transportationDestination = transportationDestination;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    public String getTolerance() {
        return tolerance;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }

    public String getAnnualCost() {
        return annualCost;
    }

    public void setAnnualCost(String annualCost) {
        this.annualCost = annualCost;
    }
}
