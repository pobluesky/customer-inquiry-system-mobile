package com.example.customer_inquiry_system_mobile.domain.question.api;

import com.example.customer_inquiry_system_mobile.domain.question.dto.QuestionResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionAPI {

    @GET("mobile/api/questions")
    Call<List<QuestionResponseDTO>> getAllQuestions();

    @GET("mobile/api/questions/{id}")
    Call<QuestionResponseDTO> getQuestionById(@Path("id") Long questionId);
}
