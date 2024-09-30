package com.example.customer_inquiry_system_mobile.domain.question.api;

import com.example.customer_inquiry_system_mobile.domain.question.dto.QuestionResponseDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface QuestionAPI {

    @GET("mobile/api/questions")
    Call<List<QuestionResponseDTO>> getAllQuestions();

    @GET("mobile/api/questions/{id}")
    Call<QuestionResponseDTO> getQuestionById(@Path("id") Long questionId);

    @GET("mobile/api/questions/search")
    Call<List<QuestionResponseDTO>> getQuestionsBySearch(
            @Query("sortBy") String sortBy,
            @QueryMap Map<String, String> queryMap
    );
}
