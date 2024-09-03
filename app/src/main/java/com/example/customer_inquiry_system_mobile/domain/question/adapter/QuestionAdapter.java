package com.example.customer_inquiry_system_mobile.domain.question.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.domain.question.dto.Question;
import com.example.customer_inquiry_system_mobile.domain.dashboard.fragment.QuestionDetailActivity;
import com.example.customer_inquiry_system_mobile.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

    private final List<Question> questionList;

    private final Context context;

    public QuestionAdapter(List<Question> questionList, Context context) {
        this.questionList = questionList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question_item, parent, false);

        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        Question question=questionList.get(position);
        holder.type.setText(question.getType());
        holder.title.setText(question.getTitle());
        holder.status.setText(question.getStatus());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuestionDetailActivity.class);

            intent.putExtra("question_id", question.getQuestionId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
