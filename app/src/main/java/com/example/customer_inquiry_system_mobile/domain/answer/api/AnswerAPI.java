package com.example.customer_inquiry_system_mobile.domain.answer.api;

import com.example.customer_inquiry_system_mobile.domain.answer.dto.AnswerResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnswerAPI {

    @GET("mobile/api/answers/{id}")
    Call<AnswerResponseDTO> getAnswerByQuestionId(@Path("id") Long questionId);
}
