package com.example.customer_inquiry_system_mobile.domain.inquiry.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewResponseDTO {

    @SerializedName("inquiryId")
    @Expose
    private Long inquiryId;

    @SerializedName("reviewText")
    @Expose
    private String reviewText;

    @SerializedName("attachmentFileName")
    @Expose
    private String attachmentFileName;

    @SerializedName("attachmentFilePath")
    @Expose
    private String attachmentFilePath;

    @SerializedName("finalReviewText")
    @Expose
    private String finalReviewText;

    @SerializedName("tsReviewReq")
    @Expose
    private String tsReviewReq;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getFinalReview() {
        return finalReviewText;
    }

    public void setFinalReview(String finalReviewText) {
        this.finalReviewText = finalReviewText;
    }
}
