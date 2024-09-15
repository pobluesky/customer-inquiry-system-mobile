package com.example.customer_inquiry_system_mobile.domain.question.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.answer.api.AnswerAPI;
import com.example.customer_inquiry_system_mobile.domain.answer.dto.AnswerResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.question.api.QuestionAPI;
import com.example.customer_inquiry_system_mobile.domain.question.dto.QuestionResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailActivity extends AppCompatActivity {

    private TextView
                    title,
                    status,
                    type,
                    questionContents,
                    answerTitle,
                    answerContents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        type = findViewById(R.id.questionType);
        status = findViewById(R.id.questionStatus);
        title = findViewById(R.id.questionTitle);
        questionContents = findViewById(R.id.questionContents);

        answerTitle = findViewById(R.id.answerTitle);
        answerContents = findViewById(R.id.answerContents);

        Long questionId = getIntent().getLongExtra("question_id", -1);

        if (questionId != -1) {
            fetchQuestionById(questionId);
            fetchAnswerByQuestionId(questionId);
        } else {
            Toast.makeText(
                    this,
                    "유효하지 않은 문의 ID입니다.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void fetchQuestionById(Long questionId) {
        RetrofitService retrofitService = new RetrofitService(null);
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
                    questionContents.setText(questionResponseDTO.getContents());

                    if ("답변대기".equals(questionResponseDTO.getStatus())) {
                        answerTitle.setText("답변 대기중입니다");
                        answerContents.setText("");

                        answerTitle.setBackgroundResource(R.drawable.rounded_box);

                        answerTitle.setGravity(Gravity.CENTER);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                (int) (150 * getResources().getDisplayMetrics().density)
                        );
                        answerTitle.setLayoutParams(params);

                        findViewById(R.id.imageView7).setVisibility(View.GONE);

                        status.setBackgroundResource(R.drawable.blue_background);
                        status.setTextColor(Color.WHITE);

                    } else if ("답변완료".equals(questionResponseDTO.getStatus())) {
                        findViewById(R.id.imageView7).setVisibility(View.VISIBLE);
                        fetchAnswerByQuestionId(questionId);

                        status.setBackgroundResource(R.drawable.red_background);
                        status.setTextColor(Color.WHITE);
                    }
                } else {
                    Toast.makeText(
                            QuestionDetailActivity.this,
                            "문의 데이터를 가져오는 데 실패했습니다.",
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



    private void fetchAnswerByQuestionId(Long questionId) {
        RetrofitService retrofitService = new RetrofitService(null);
        AnswerAPI answerAPI = retrofitService.getRetrofit().create(AnswerAPI.class);

        answerAPI.getAnswerByQuestionId(questionId).enqueue(new Callback<AnswerResponseDTO>() {
            @Override
            public void onResponse(
                    @NonNull Call<AnswerResponseDTO> call,
                    @NonNull Response<AnswerResponseDTO> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    AnswerResponseDTO answerResponseDTO = response.body();

                    answerTitle.setText(answerResponseDTO.getTitle());
                    answerContents.setText(answerResponseDTO.getContents());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AnswerResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(
                        QuestionDetailActivity.this,
                        "Answer API 호출 실패: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
