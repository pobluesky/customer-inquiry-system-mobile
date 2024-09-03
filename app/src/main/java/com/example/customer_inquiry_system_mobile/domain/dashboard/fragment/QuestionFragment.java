package com.example.customer_inquiry_system_mobile.domain.dashboard.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.question.adapter.QuestionAdapter;
import com.example.customer_inquiry_system_mobile.domain.question.api.QuestionAPI;
import com.example.customer_inquiry_system_mobile.domain.question.dto.Question;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionFragment extends Fragment {

    private RecyclerView recyclerView;

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container,false);

        recyclerView = rootView.findViewById(R.id.questionList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadQuestions();

        return rootView;
    }

    private void loadQuestions() {
        RetrofitService retrofitService = new RetrofitService();
        QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);

        questionAPI.getAllQuestions().enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(@NonNull Call<List<Question>> call, @NonNull Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    populateListView(response.body());
                } else {
                    Toast.makeText(
                            getContext(),
                            "Failed to load questions: " + response.message(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Question>> call, @NonNull Throwable t) {
                Toast.makeText(
                        getContext(),
                        "Failed to load questions",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void populateListView(List<Question> questionList) {
        QuestionAdapter questionAdapter = new QuestionAdapter(questionList, getContext());
        recyclerView.setAdapter(questionAdapter);
    }
}
