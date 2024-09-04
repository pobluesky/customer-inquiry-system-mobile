package com.example.customer_inquiry_system_mobile.global;

import com.example.customer_inquiry_system_mobile.domain.mypage.api.UserApi;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        // HTTP 로깅 인터셉터 추가 (디버그 용도)
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttpClient 설정
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        // Retrofit 초기화
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // 로컬 호스트 주소 (에뮬레이터용)
                .client(client) // OkHttpClient 추가
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    // UserApiService 인스턴스를 제공하는 메서드 추가
    public UserApi getUserApi() {
        return retrofit.create(UserApi.class);
    }
}
