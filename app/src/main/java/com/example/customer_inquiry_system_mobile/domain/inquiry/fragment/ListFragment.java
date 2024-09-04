package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

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
import com.example.customer_inquiry_system_mobile.domain.inquiry.adapter.InquiryAdapter;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquirySummaryResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.api.InquiryAPI;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;

    public ListFragment() {}

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = rootView.findViewById(R.id.inquiryList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadInquiries();

        return rootView;
    }

    private void loadInquiries() {
        RetrofitService retrofitService = new RetrofitService();
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        inquiryAPI.getAllInquiries().enqueue(new Callback<List<InquirySummaryResponseDTO>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<InquirySummaryResponseDTO>> call,
                    @NonNull Response<List<InquirySummaryResponseDTO>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    populateListView(response.body());
                } else {
                    Toast.makeText(
                            getContext(),
                            "Failed to load inquiries: " + response.message(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<InquirySummaryResponseDTO>> call, @NonNull Throwable t) {
                Toast.makeText(
                        getContext(),
                        "Failed to load inquiries",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void populateListView(List<InquirySummaryResponseDTO> inquirySummaryResponseDTOList) {
        InquiryAdapter inquiryAdapter = new InquiryAdapter(inquirySummaryResponseDTOList, getContext());
        recyclerView.setAdapter(inquiryAdapter);
    }
}
