package com.example.customer_inquiry_system_mobile.domain.question.fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.question.api.QuestionAPI;
import com.example.customer_inquiry_system_mobile.domain.question.dto.QuestionResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionDetailActivity extends AppCompatActivity {

    private TextView title ,status, type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        type = findViewById(R.id.questionTypeDetail);
        status = findViewById(R.id.statusDetail);
        title = findViewById(R.id.titleDetail);

        Long questionId = getIntent().getLongExtra("question_id", -1);

        if (questionId != -1) {
            fetchQuestionById(questionId);
        } else {
            Toast.makeText(
                    this,
                    "유효하지 않은 문의 ID입니다.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void fetchQuestionById(Long questionId) {
        RetrofitService retrofitService = new RetrofitService();
        QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);

        questionAPI.getQuestionById(questionId).enqueue(new Callback<QuestionResponseDTO>() {
            @Override
            public void onResponse(
                    @NonNull Call<QuestionResponseDTO> call,
                    @NonNull Response<QuestionResponseDTO> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    QuestionResponseDTO questionResponseDTO = response.body();

                    type.setText(questionResponseDTO.getType());
                    status.setText(questionResponseDTO.getStatus());
                    title.setText(questionResponseDTO.getTitle());
                } else {
                    Toast.makeText(
                            QuestionDetailActivity.this,
                            "데이터를 가져오는 데 실패했습니다.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuestionResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(
                        QuestionDetailActivity.this,
                        "API 호출 실패: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
