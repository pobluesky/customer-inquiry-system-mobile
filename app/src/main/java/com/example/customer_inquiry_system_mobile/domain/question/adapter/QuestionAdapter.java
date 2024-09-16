package com.example.customer_inquiry_system_mobile.domain.question.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.domain.question.dto.QuestionResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.question.fragment.QuestionDetailActivity;
import com.example.customer_inquiry_system_mobile.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

    private final List<QuestionResponseDTO> questionResponseDTOList;

    private final Context context;

    public QuestionAdapter(List<QuestionResponseDTO> questionResponseDTOList, Context context) {
        this.questionResponseDTOList = questionResponseDTOList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.list_question_item,
                        parent,
                        false
                );

        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        QuestionResponseDTO questionResponseDTO = questionResponseDTOList.get(position);

        holder.type.setText(questionResponseDTO.getType());
        holder.title.setText(questionResponseDTO.getTitle());
        holder.status.setText(questionResponseDTO.getStatus());

        String status = questionResponseDTO.getStatus();

        int paddingDp = (int) (5 * context.getResources().getDisplayMetrics().density);
        holder.status.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);

        if ("답변대기".equals(status)) {
            holder.status.setBackgroundResource(R.drawable.blue_background);
            holder.status.setTextColor(Color.WHITE);
        } else if ("답변완료".equals(status)) {
            holder.status.setBackgroundResource(R.drawable.red_background);
            holder.status.setTextColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuestionDetailActivity.class);

            intent.putExtra("question_id", questionResponseDTO.getQuestionId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return questionResponseDTOList.size();
    }
}
