//package com.example.customer_inquiry_system_mobile.domain.dashboard.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.customer_inquiry_system_mobile.R;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link DashboardFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class DashboardFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public DashboardFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DashboardFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DashboardFragment newInstance(String param1, String param2) {
//        DashboardFragment fragment = new DashboardFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        ViewGroup rootView = (ViewGroup)inflater.inflate(
//                R.layout.fragment_dashboard,
//                container,
//                false
//        );
//
//        return rootView;
//    }
//}

package com.example.customer_inquiry_system_mobile.domain.dashboard.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.dashboard.api.DashboardAPI;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private DashboardAPI dashboardAPI;

    private TextView
            textViewMonthly,
            textViewProgress,
            textViewPercentage,
            textViewProductType;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(
                R.layout.fragment_dashboard,
                container,
                false
        );

        RetrofitService retrofitService = new RetrofitService(null);
        dashboardAPI = retrofitService.getRetrofit().create(DashboardAPI.class);

        textViewMonthly = view.findViewById(R.id.textViewMonthly);
        textViewProgress = view.findViewById(R.id.textViewProgress);
        textViewPercentage = view.findViewById(R.id.textViewPercentage);
        textViewProductType = view.findViewById(R.id.textViewProductType);

        SharedPreferences prefs = getActivity().getSharedPreferences("my_prefs", MODE_PRIVATE);
        String token = prefs.getString("token", null); // 저장된 token 가져오기

        if (token != null && !token.isEmpty()) {
            fetchDashboardData(token);
//            } else {
//                Toast.makeText(
//                        getActivity(),
//                        "유효하지 않은 토큰입니다.",
//                        Toast.LENGTH_SHORT
//                ).show();
//            }
        } else {
            Toast.makeText(getActivity(),
                    "유효하지 않은 정보입니다.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        return view;
    }

    private void fetchDashboardData(String token) {
        // 평균 월별 문의 데이터
        dashboardAPI.getAverageMonthlyInquiry(token).enqueue(new Callback<Map<String, List<Object[]>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<Map<String, List<Object[]>>> call,
                    @NonNull Response<Map<String, List<Object[]>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, List<Object[]>> data = response.body();
                    textViewMonthly.setText(data.toString()); // 실제 데이터 처리 필요
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<Object[]>>> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
            }
        });

        // 진행 상태별 문의 수 데이터
        dashboardAPI.getInquiryCountsByProgress(token).enqueue(new Callback<Map<String, List<Object[]>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<Map<String, List<Object[]>>> call,
                    @NonNull Response<Map<String, List<Object[]>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, List<Object[]>> data = response.body();
                    textViewProgress.setText(data.toString());
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<Object[]>>> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
            }
        });

        // 완료/미완료 비율 데이터
        dashboardAPI.getInquiryPercentageCompletedUncompleted(token).enqueue(new Callback<Map<String, Map<String, String>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<Map<String, Map<String, String>>> call,
                    @NonNull Response<Map<String, Map<String, String>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Map<String, String>> data = response.body();
                    textViewPercentage.setText(data.toString());
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Map<String, String>>> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
            }
        });

        // 제품 유형별 문의 수 데이터
        dashboardAPI.getInquiryCountsByProductType(token).enqueue(new Callback<Map<String, List<Object[]>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<Map<String, List<Object[]>>> call,
                    @NonNull Response<Map<String, List<Object[]>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, List<Object[]>> data = response.body();
                    textViewProductType.setText(data.toString());
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<Object[]>>> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
            }
        });
    }

    private void handleErrorResponse(Response<?> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Toast.makeText(getActivity(), "데이터를 가져오지 못했습니다: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "오류 발생: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
