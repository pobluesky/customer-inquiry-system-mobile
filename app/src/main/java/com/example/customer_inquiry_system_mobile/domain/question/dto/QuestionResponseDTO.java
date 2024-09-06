package com.example.customer_inquiry_system_mobile.domain.question.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionResponseDTO {

    @SerializedName("questionId")
    @Expose
    Long questionId;

    @SerializedName("customer")
    @Expose
    String customer;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("contents")
    @Expose
    String contents;

    public Long getQuestionId() {
        return questionId;
    }

    public String getCustomer() { return customer;}

    public String getTitle() { return title;}

    public String getStatus() { return status;}

    public String getType() { return type;}

    public String getContents() { return contents;}
}
