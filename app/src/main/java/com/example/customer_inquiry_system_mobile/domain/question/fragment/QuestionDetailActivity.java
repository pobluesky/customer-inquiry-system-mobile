package com.example.customer_inquiry_system_mobile.domain.question.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailActivity extends AppCompatActivity {

    private TextView
                    inquiryNoLabel,
                    inquiryNo,
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

        inquiryNoLabel = findViewById(R.id.inquiryNoLabel);
        inquiryNo = findViewById(R.id.inquiryNo);
        type = findViewById(R.id.questionType);
        status = findViewById(R.id.questionStatus);
        title = findViewById(R.id.questionTitle);
        questionContents = findViewById(R.id.questionContents);

        answerTitle = findViewById(R.id.answerTitle);
        answerContents = findViewById(R.id.answerContents);

        Long questionId = getIntent().getLongExtra("question_id", -1);
        Long inquiryId = getIntent().getLongExtra("inquiry_id", -1);

        if (questionId != -1) {
            fetchQuestionById(questionId, inquiryId);
            fetchAnswerByQuestionId(questionId);
        }

        ImageView revertIcon = findViewById(R.id.revertIcon);
        revertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetchQuestionById(Long questionId, Long inquiryId) {
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

                    if(inquiryId != null && Objects.equals(questionResponseDTO.getType(), "주문문의")){
                        inquiryNo.setText(String.valueOf(inquiryId));
                        inquiryNoLabel.setVisibility(View.VISIBLE);
                    }

                    if ("답변대기".equals(questionResponseDTO.getStatus())) {
                        answerTitle.setText("답변 대기중입니다");
                        answerContents.setText("");

                        int paddingDp = (int) (5 * getResources().getDisplayMetrics().density);
                        status.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);

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

                        int paddingDp = (int) (5 * getResources().getDisplayMetrics().density);
                        status.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);

                        status.setBackgroundResource(R.drawable.red_background);
                        status.setTextColor(Color.WHITE);
                    }
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
