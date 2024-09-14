package com.example.customer_inquiry_system_mobile.domain.dashboard.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private DashboardAPI dashboardAPI;

    private LineChart lineChartMonthly;

    private PieChart pieChartTotal, pieChartManager;

    private CombinedChart combinedChart;

    private RadarChart radarChart;

    private TextView
            textViewName,
            textViewEmail,
            textViewPhone,
            textViewEmpNo,
            textViewRole,
            textViewDepartment;

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

        lineChartMonthly = view.findViewById(R.id.lineChartMonthly); // 평균 월별 문의 데이터
        combinedChart = view.findViewById(R.id.combinedChart); // 진행 상태별 문의 수 데이터
        pieChartTotal = view.findViewById(R.id.pieChartTotal); // 전체 완료/미완료 문의 수 데이터
        pieChartManager = view.findViewById(R.id.pieChartManager); // 매니저 완료/미완료 문의 수 데이터
        radarChart = view.findViewById(R.id.radarChart); // 제품 유형별 문의 수 데이터
        
        SharedPreferences prefs = getActivity().getSharedPreferences("my_prefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token != null && !token.isEmpty()) {
            fetchDashboardData(token);
        } else {
            Toast.makeText(getActivity(),
                    "유효하지 않은 회원입니다.",
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
                    updateLineChart(data);
                    setupXAxis(combinedChart);
                } else {
                    errorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<Object[]>>> call, Throwable t) {
                Toast.makeText(
                        getActivity(),
                        "오류 발생: " + t.getMessage()
                        , Toast.LENGTH_SHORT
                ).show();
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
                    updateCombinedChart(data);
                } else {
                    errorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<Object[]>>> call, Throwable t) {
                Toast.makeText(
                        getActivity(),
                        "오류 발생: " + t.getMessage()
                        , Toast.LENGTH_SHORT
                ).show();
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
                    updatePieCharts(data);
                } else {
                    errorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Map<String, String>>> call, Throwable t) {
                Toast.makeText(
                        getActivity(),
                        "오류 발생: " + t.getMessage()
                        , Toast.LENGTH_SHORT
                ).show();
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
                    updateRadarChart(data);
                } else {
                    errorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<Object[]>>> call, Throwable t) {
                Toast.makeText(
                        getActivity(),
                        "오류 발생: " + t.getMessage()
                        , Toast.LENGTH_SHORT
                ).show();
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
        totalDataSet.setColor(Color.rgb(0, 150, 136)); // 라인 색상
        totalDataSet.setValueTextColor(Color.rgb(0, 150, 136)); // 값 텍스트 색상
        totalDataSet.setLineWidth(4f); // 라인 두께
        totalDataSet.setCircleColor(Color.rgb(0, 150, 136)); // 원 색상
        totalDataSet.setCircleRadius(6f); // 원 크기
        totalDataSet.setDrawCircleHole(false); // 원 내부의 구멍
        totalDataSet.setDrawFilled(true); // 채우기 표시
        totalDataSet.setFillColor(Color.rgb(0, 150, 136)); // 채우기 색상
        totalDataSet.setFillAlpha(0); // 채우기 투명도 (0: 완전 투명)
        totalDataSet.setMode(LineDataSet.Mode.LINEAR); // 직선 모드

        LineDataSet managerDataSet = new LineDataSet(managerEntries, "Manager");
        managerDataSet.setColor(Color.rgb(255, 87, 34)); // 라인 색상
        managerDataSet.setValueTextColor(Color.rgb(255, 87, 34)); // 값 텍스트 색상
        managerDataSet.setLineWidth(4f); // 라인 두께
        managerDataSet.setCircleColor(Color.rgb(255, 87, 34)); // 원 색상
        managerDataSet.setCircleRadius(6f); // 원 크기
        managerDataSet.setDrawCircleHole(false); // 원 내부의 구멍
        managerDataSet.setDrawFilled(true); // 채우기 표시
        managerDataSet.setFillColor(Color.rgb(255, 87, 34)); // 채우기 색상
        managerDataSet.setFillAlpha(0); // 채우기 투명도 (0: 완전 투명)
        managerDataSet.setMode(LineDataSet.Mode.LINEAR); // 직선 모드

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

    private void updatePieCharts(Map<String, Map<String, String>> data) {
        if (data != null) {
            Map<String, String> totalData = data.get("total");
            Map<String, String> managerData = data.get("manager");

            // Total Pie Chart
            List<PieEntry> totalEntries = new ArrayList<>();
            if (totalData != null) {
                for (Map.Entry<String, String> entry : totalData.entrySet()) {
                    float value = Float.parseFloat(entry.getValue());
                    totalEntries.add(new PieEntry(value, entry.getKey()));
                }
            }

            PieDataSet totalDataSet = new PieDataSet(totalEntries, "Total");
            totalDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            totalDataSet.setValueTextColor(Color.BLACK);
            totalDataSet.setValueTextSize(16f);

            PieData totalPieData = new PieData(totalDataSet);
            pieChartTotal.setData(totalPieData);
            pieChartTotal.invalidate();

            // Manager Pie Chart
            List<PieEntry> managerEntries = new ArrayList<>();
            if (managerData != null) {
                for (Map.Entry<String, String> entry : managerData.entrySet()) {
                    float value = Float.parseFloat(entry.getValue());
                    managerEntries.add(new PieEntry(value, entry.getKey()));
                }
            }

            PieDataSet managerDataSet = new PieDataSet(managerEntries, "Manager");
            managerDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            managerDataSet.setValueTextColor(Color.BLACK);
            managerDataSet.setValueTextSize(16f);

            PieData managerPieData = new PieData(managerDataSet);
            pieChartManager.setData(managerPieData);
            pieChartManager.invalidate();
        }
    }

    private void updateCombinedChart(Map<String, List<Object[]>> data) {
        List<BarEntry> barEntries = new ArrayList<>();
        List<Entry> lineEntries = new ArrayList<>();

        // 카테고리 순서 정의
        String[] categories = {
                "SUBMIT", "RECEIPT", "QUALITY_REVIEW_REQUEST",
                "QUALITY_REVIEW_RESPONSE", "QUALITY_REVIEW_COMPLETED", "FINAL_REVIEW_COMPLETED"
        };

        // Total 데이터 (막대 차트)
        List<Object[]> totalData = data.get("total");
        for (int i = 0; i < categories.length; i++) {
            float value = 0f;
            for (Object[] entry : totalData) {
                if (categories[i].equals(entry[0].toString())) {
                    value = Float.parseFloat(entry[1].toString());
                    break;
                }
            }
            barEntries.add(new BarEntry(i, value));
        }

        // Manager 데이터 (선형 차트)
        List<Object[]> managerData = data.get("manager");
        for (int i = 0; i < categories.length; i++) {
            float value = 0f;
            for (Object[] entry : managerData) {
                if (categories[i].equals(entry[0].toString())) {
                    value = Float.parseFloat(entry[1].toString());
                    break;
                }
            }
            lineEntries.add(new Entry(i, value));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Total");
        barDataSet.setColor(Color.rgb(0, 150, 136));

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Manager");
        lineDataSet.setColor(Color.rgb(255, 87, 34));
        lineDataSet.setCircleColor(Color.rgb(255, 87, 34));

        CombinedData combinedData = new CombinedData();
        combinedData.setData(new BarData(barDataSet));
        combinedData.setData(new LineData(lineDataSet));

        combinedChart.setData(combinedData);
        setupXAxis(combinedChart);
        combinedChart.invalidate();
    }

    private void setupXAxis(CombinedChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[] {
                "step 1", "step 2", "step 3", "step 4", "step 5", "step 6", "step 7"
        }));
    }

    private void updateRadarChart(Map<String, List<Object[]>> data) {
        // Total 데이터와 Manager 데이터 추출
        List<Object[]> totalDataArray = data.get("total");
        List<Object[]> managerDataArray = data.get("manager");

        // 카테고리 목록 정의
        List<String> categories = List.of("CAR", "HOT_ROLLED", "WIRE_ROD", "THICK_PLATE", "COLD_ROLLED");

        Map<String, Float> totalData = new HashMap<>();
        Map<String, Float> managerData = new HashMap<>();

        // Total 데이터 가공
        if (totalDataArray != null) {
            for (Object[] entry : totalDataArray) {
                String category = (String) entry[0];
                Float value = Float.parseFloat(entry[1].toString());
                totalData.put(category, value);
            }
        }

        // Manager 데이터 가공
        if (managerDataArray != null) {
            for (Object[] entry : managerDataArray) {
                String category = (String) entry[0];
                Float value = Float.parseFloat(entry[1].toString());
                managerData.put(category, value);
            }
        }

        // 카테고리별 데이터 세팅
        List<RadarEntry> totalEntries = new ArrayList<>();
        List<RadarEntry> managerEntries = new ArrayList<>();

        for (String category : categories) {
            float totalValue = totalData.getOrDefault(category, 0.0f);
            float managerValue = managerData.getOrDefault(category, 0.0f);

            totalEntries.add(new RadarEntry(totalValue));
            managerEntries.add(new RadarEntry(managerValue));
        }

        RadarDataSet totalDataSet = new RadarDataSet(totalEntries, "Total");
        totalDataSet.setColor(Color.rgb(54, 183, 0));  // 밝은 초록색
        totalDataSet.setFillColor(Color.rgb(54, 183, 0));  // 밝은 초록색
        totalDataSet.setDrawFilled(true);
        totalDataSet.setFillAlpha(100);

        RadarDataSet managerDataSet = new RadarDataSet(managerEntries, "Manager");
        managerDataSet.setColor(Color.rgb(255, 0, 102));  // 빨간색
        managerDataSet.setFillColor(Color.rgb(255, 0, 102));  // 빨간색
        managerDataSet.setDrawFilled(true);
        managerDataSet.setFillAlpha(100);

        RadarData radarData = new RadarData(totalDataSet, managerDataSet);
        radarChart.setData(radarData);
        radarChart.getDescription().setEnabled(false);
        radarChart.getLegend().setTextColor(Color.BLACK);

        // 레이더 차트 내부 간격마다 숫자 삭제
        radarChart.getYAxis().setDrawLabels(false);

        // 레이더 차트 내부 영역 꼭짓점의 숫자 삭제
        radarChart.getXAxis().setDrawLabels(false);

        // 레이더 차트 외부 숫자 크기 조정 및 간격 조정
        radarChart.getYAxis().setTextSize(14f);  // 외부 숫자 크기 조정
        radarChart.getYAxis().setYOffset(10f);  // 외부 숫자와 차트 사이 간격 조정

        // 범례 중앙 정렬 및 크기 조정
        Legend legend = radarChart.getLegend();
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setXOffset(0f);  // X 오프셋 조정
//        legend.setYOffset(10f); // Y 오프셋 조정
//        legend.setTextSize(16f);  // 범례 텍스트 크기
//        legend.setFormSize(20f);  // 범례 도형 크기
//        legend.setFormToTextSpace(10f);  // 도형과 텍스트 사이의 간격

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);  // 범례 방향을 수직으로 설정
        legend.setXOffset(0f);  // X 오프셋 조정
        legend.setYOffset(20f); // Y 오프셋 조정
        legend.setTextSize(16f);  // 범례 텍스트 크기 조정
        legend.setFormSize(20f);  // 범례 도형 크기 조정
        legend.setFormToTextSpace(15f);  // 도형과 텍스트 사이의 간격 조정
        legend.setStackSpace(10f);  // 줄 사이의 간격 조정


        radarChart.invalidate();
    }


    private void errorResponse(Response<?> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Toast.makeText(getActivity(), "데이터를 가져오지 못했습니다: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "오류 발생: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
