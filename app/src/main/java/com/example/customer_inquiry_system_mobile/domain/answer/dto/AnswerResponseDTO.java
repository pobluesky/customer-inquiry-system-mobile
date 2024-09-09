package com.example.customer_inquiry_system_mobile.domain.answer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerResponseDTO {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("contents")
    @Expose
    private String contents;

    public String getTitle() { return title;}

    public String getContents() { return contents;}
}
