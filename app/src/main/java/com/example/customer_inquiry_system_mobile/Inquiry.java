package com.example.customer_inquiry_system_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inquiry {
    @SerializedName("inquiryId")
    @Expose
    Long inquiryId;

    @SerializedName("salesPerson")
    @Expose
    String salesPerson; //판매 계약자 e.g. 현대종합상사(주)

    @SerializedName("progress")
    @Expose
    String progress;  //진행현황 e.g. 접수 -> 1차검토 -> ..

    @SerializedName("productType")
    @Expose
    String productType; //제품구분 e.g. 자동차, 열연, ..

    @SerializedName("inquiryType")
    @Expose
    String inquiryType; //유형 e.g 품질문의, 공통(견적/품질문의)

    @SerializedName("customerName")
    @Expose
    String customerName;  //고객사명 e.g. AAT

    @SerializedName("industry")
    @Expose
    String industry; //산업 분류 e.g. AUTOMOBILE

    @SerializedName("salesManagerName")
    @Expose
    String salesManagerName;

    @SerializedName("qualityManagerName")
    @Expose
    String qualityManagerName;

    public Long getInquiryId() {
        return inquiryId;
    }

    public String getSalesPerson() {
        return salesPerson;
    }
}
