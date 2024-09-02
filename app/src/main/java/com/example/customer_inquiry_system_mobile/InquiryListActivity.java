package com.example.customer_inquiry_system_mobile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.adapter.InquiryAdapter;
import com.example.customer_inquiry_system_mobile.retrofit.InquiryAPI;
import com.example.customer_inquiry_system_mobile.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_list);

        // 기본적인 recyclerview 설정
        recyclerView = findViewById(R.id.inquiryList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadInquiries();
    }

    private void loadInquiries(){
        RetrofitService retrofitService=new RetrofitService();
        InquiryAPI inquiryAPI=retrofitService.getRetrofit().create(InquiryAPI.class);
        inquiryAPI.getAllInquiries()
                .enqueue(new Callback<List<Inquiry>>() {
                    @Override
                    public void onResponse(Call<List<Inquiry>> call, Response<List<Inquiry>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Inquiry>> call, Throwable t) {
                        Toast.makeText(InquiryListActivity.this, "Failed to load inquiries", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void populateListView(List<Inquiry> inquiryList) {
        InquiryAdapter inquiryAdapter = new InquiryAdapter(inquiryList, this); // `this` refers to the Activity context
        recyclerView.setAdapter(inquiryAdapter);
    }

}