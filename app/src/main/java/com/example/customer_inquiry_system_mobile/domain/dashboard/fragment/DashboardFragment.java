package com.example.customer_inquiry_system_mobile.domain.dashboard.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private DashboardAPI dashboardAPI;

    private LineChart lineChartMonthly;

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

//        textViewMonthly = view.findViewById(R.id.textViewMonthly);
        lineChartMonthly = view.findViewById(R.id.lineChartMonthly);
        textViewProgress = view.findViewById(R.id.textViewProgress);
        textViewPercentage = view.findViewById(R.id.textViewPercentage);
        textViewProductType = view.findViewById(R.id.textViewProductType);

        SharedPreferences prefs = getActivity().getSharedPreferences("my_prefs", MODE_PRIVATE);
        String token = prefs.getString("token", null); // 저장된 token 가져오기

        if (token != null && !token.isEmpty()) {
            fetchDashboardData(token);
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
                    String formattedData = formatInquiryData(data);
//                    textViewMonthly.setText(formattedData);
                    updateLineChart(data);
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
                    String formattedData = formatProgressData(data);
                    textViewProgress.setText(formattedData);
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
                    String formattedData = formatPercentageData(data);
                    textViewPercentage.setText(formattedData);
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
                    String formattedData = formatProductTypeData(data);
                    textViewProductType.setText(formattedData);
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

    private void updateLineChart(Map<String, List<Object[]>> data) {
        List<Entry> totalEntries = new ArrayList<>();
        List<Entry> managerEntries = new ArrayList<>();

        List<Object[]> totalData = data.get("total");
        List<Object[]> managerData = data.get("manager");

        for (Object[] entry : totalData) {
            float month = Float.parseFloat(entry[0].toString());
            float value = Float.parseFloat(entry[1].toString());
            totalEntries.add(new Entry(month, value));
        }

        for (Object[] entry : managerData) {
            float month = Float.parseFloat(entry[0].toString());
            float value = Float.parseFloat(entry[1].toString());
            managerEntries.add(new Entry(month, value));
        }

        LineDataSet totalDataSet = new LineDataSet(totalEntries, "Total");
        totalDataSet.setColor(Color.rgb(0, 150, 136)); // 색상
        totalDataSet.setValueTextColor(Color.rgb(0, 150, 136)); // 값 텍스트 색상
        totalDataSet.setLineWidth(4f); // 라인 두께
        totalDataSet.setCircleColor(Color.rgb(0, 150, 136)); // 원 색상
        totalDataSet.setCircleRadius(6f); // 원 크기
        totalDataSet.setDrawCircleHole(false); // 원 내부의 구멍
        totalDataSet.setDrawFilled(true);
        totalDataSet.setFillColor(Color.rgb(0, 150, 136)); // 채우기 색상
        totalDataSet.setFillAlpha(80); // 채우기 투명도
        totalDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // 부드러운 선

        LineDataSet managerDataSet = new LineDataSet(managerEntries, "Manager");
        managerDataSet.setColor(Color.rgb(255, 87, 34)); // 색상
        managerDataSet.setValueTextColor(Color.rgb(255, 87, 34)); // 값 텍스트 색상
        managerDataSet.setLineWidth(4f); // 라인 두께
        managerDataSet.setCircleColor(Color.rgb(255, 87, 34)); // 원 색상
        managerDataSet.setCircleRadius(6f); // 원 크기
        managerDataSet.setDrawCircleHole(false); // 원 내부의 구멍
        managerDataSet.setDrawFilled(true);
        managerDataSet.setFillColor(Color.rgb(255, 87, 34)); // 채우기 색상
        managerDataSet.setFillAlpha(80); // 채우기 투명도
        managerDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // 부드러운 선

        LineData lineData = new LineData(totalDataSet, managerDataSet);
        lineChartMonthly.setData(lineData);

        // Customize X-axis
        XAxis xAxis = lineChartMonthly.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Only intervals of 1
        xAxis.setLabelCount(12, true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(14f); // 라벨 크기
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        // Customize Y-axis
        YAxis leftAxis = lineChartMonthly.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(14f); // 라벨 크기
        leftAxis.setGranularity(1f);

        YAxis rightAxis = lineChartMonthly.getAxisRight();
        rightAxis.setEnabled(false);

        // Customize chart background
        lineChartMonthly.setBackgroundColor(Color.WHITE);
        lineChartMonthly.setDrawGridBackground(true);
        lineChartMonthly.setGridBackgroundColor(Color.rgb(248, 248, 248)); // 그리드 배경 색상

        // Enable legend
        lineChartMonthly.getLegend().setEnabled(true);
        lineChartMonthly.getLegend().setTextColor(Color.BLACK);
        lineChartMonthly.getLegend().setTextSize(14f);

        // Enable description
        lineChartMonthly.getDescription().setEnabled(true);
        lineChartMonthly.getDescription().setText("Monthly Data Comparison");
        lineChartMonthly.getDescription().setTextSize(16f);
        lineChartMonthly.getDescription().setTextColor(Color.BLACK);

        // Animate chart
        lineChartMonthly.animateXY(0, 250);

        lineChartMonthly.invalidate(); // Refresh the chart
    }

    private String formatInquiryData(Map<String, List<Object[]>> data) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Object[]>> entry : data.entrySet()) {
            result.append(entry.getKey()).append(":\n");
            for (Object[] arr : entry.getValue()) {
                result.append("- ").append(arr[0].toString()).append(": ").append(arr[1].toString()).append("\n");
            }
        }
        return result.toString();
    }

    private String formatProgressData(Map<String, List<Object[]>> data) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Object[]>> entry : data.entrySet()) {
            result.append(entry.getKey()).append(":\n");
            for (Object[] arr : entry.getValue()) {
                result.append("- ").append(arr[0].toString()).append(": ").append(arr[1].toString()).append("\n");
            }
        }
        return result.toString();
    }

    private String formatPercentageData(Map<String, Map<String, String>> data) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            result.append(entry.getKey()).append(":\n");
            for (Map.Entry<String, String> subEntry : entry.getValue().entrySet()) {
                result.append("- ").append(subEntry.getKey()).append(": ").append(subEntry.getValue()).append("\n");
            }
        }
        return result.toString();
    }

    private String formatProductTypeData(Map<String, List<Object[]>> data) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Object[]>> entry : data.entrySet()) {
            result.append(entry.getKey()).append(":\n");
            for (Object[] arr : entry.getValue()) {
                result.append("- ").append(arr[0].toString()).append(": ").append(arr[1].toString()).append("\n");
            }
        }
        return result.toString();
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