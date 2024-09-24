package com.example.customer_inquiry_system_mobile.domain.question.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionResponseDTO {

    @SerializedName("inquiryId")
    @Expose
    private Long inquiryId;

    @SerializedName("questionId")
    @Expose
    private Long questionId;

    @SerializedName("customer")
    @Expose
    private String customer;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("contents")
    @Expose
    private String contents;

    public Long getInquiryId() {
        return inquiryId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getCustomer() { return customer;}

    public String getTitle() { return title;}

    public String getStatus() { return status;}

    public String getType() { return type;}

    public String getContents() { return contents;}
}
