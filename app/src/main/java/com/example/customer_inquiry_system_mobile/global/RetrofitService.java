package com.example.customer_inquiry_system_mobile.global;

import com.example.customer_inquiry_system_mobile.domain.inquiry.LineItemDeserializer;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.LineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.mypage.api.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService(String productType) {
        initializeRetrofit(productType);
    }

    private void initializeRetrofit(String productType) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(LineItemResponseDTO.class, new LineItemDeserializer(productType));

        Gson gson = gsonBuilder.create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public UserApi getUserApi() {
        return retrofit.create(UserApi.class);
    }
}
