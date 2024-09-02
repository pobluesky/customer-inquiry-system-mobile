package com.example.customer_inquiry_system_mobile.retrofit;

import com.example.customer_inquiry_system_mobile.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionAPI {

    @GET("mobile/api/questions")
    Call<List<Question>> getAllQuestions();

    @GET("mobile/api/questions/{id}")
    Call<Question> getQuestionById(@Path("id") Long questionId);
}
