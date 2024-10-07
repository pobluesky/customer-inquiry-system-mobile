package com.example.customer_inquiry_system_mobile.domain.dashboard.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.dashboard.api.DashboardAPI;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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

        parseChartData(data.get("total"), totalEntries);
        parseChartData(data.get("manager"), managerEntries);

        LineDataSet totalDataSet = createLineDataSet(totalEntries, "Total", "#0678BF");
        LineDataSet managerDataSet = createLineDataSet(managerEntries, "Manager", "#F28080");

        LineData lineData = new LineData(totalDataSet, managerDataSet);
        lineChartMonthly.setData(lineData);

        configureLineChartAppearance();
    }

    private void parseChartData(List<Object[]> data, List<Entry> entries) {
        if (data != null) {
            for (Object[] entry : data) {
                float month = Float.parseFloat(entry[0].toString());
                float value = Float.parseFloat(entry[1].toString());
                entries.add(new Entry(month, value));
            }
        }
    }

    private LineDataSet createLineDataSet(List<Entry> entries, String label, String color) {
        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(Color.parseColor(color));
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(4f);
        dataSet.setCircleColor(Color.parseColor(color));
        dataSet.setCircleRadius(6f);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor(color));
        dataSet.setFillAlpha(0);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        return dataSet;
    }

    private void configureLineChartAppearance() {
        XAxis xAxis = lineChartMonthly.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12, true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(14f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = lineChartMonthly.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(14f);
        leftAxis.setGranularity(1f);
        leftAxis.setXOffset(10f);

        YAxis rightAxis = lineChartMonthly.getAxisRight();
        rightAxis.setEnabled(false);

        lineChartMonthly.setExtraBottomOffset(10f);
        lineChartMonthly.setBackgroundColor(Color.WHITE);
        lineChartMonthly.setDrawGridBackground(true);
        lineChartMonthly.setGridBackgroundColor(Color.rgb(248, 248, 248));
        lineChartMonthly.getLegend().setEnabled(false);
        lineChartMonthly.getDescription().setEnabled(false);
        lineChartMonthly.animateXY(0, 250);
        lineChartMonthly.invalidate();
    }

    private void updatePieCharts(Map<String, Map<String, String>> data) {
        if (data == null) {
            return;
        }

        Map<String, String> totalData = data.get("total");
        Map<String, String> managerData = data.get("manager");

        updatePieChart(pieChartTotal, totalData, "Total",
                new int[] { Color.rgb(37, 217, 199), Color.rgb(6, 120, 191) });

        updatePieChart(pieChartManager, managerData, "Manager",
                new int[] { Color.rgb(242, 233, 187), Color.rgb(242, 128, 128) });
    }

    private void updatePieChart(PieChart pieChart, Map<String, String> data, String label, int[] colors) {
        if (data == null) {
            pieChart.clear();
            return;
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            float value = Float.parseFloat(entry.getValue());
            if (value > 0 || pieChart.equals(pieChartTotal)) { // Include 0 values only for total chart
                entries.add(new PieEntry(value, entry.getKey()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, label);
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.animateY(1000, Easing.EaseInOutCubic);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
    }

    private void updateCombinedChart(Map<String, List<Object[]>> data) {
        if (data == null) {
            return;
        }

        String[] categories = {
                "SUBMIT",
                "RECEIPT",
                "QUALITY_REVIEW_REQUEST",
                "QUALITY_REVIEW_RESPONSE",
                "QUALITY_REVIEW_COMPLETED",
                "FINAL_REVIEW_COMPLETED"
        };

        List<BarEntry> barEntries = createBarEntries(data.get("total"), categories);
        List<Entry> lineEntries = createLineEntries(data.get("manager"), categories);

        BarDataSet barDataSet = createBarDataSet(barEntries);
        LineDataSet lineDataSet = createLineDataSet(lineEntries);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(new BarData(barDataSet));
        combinedData.setData(new LineData(lineDataSet));

        configureChart(combinedChart, combinedData);
        setupXAxis(combinedChart);
        combinedChart.invalidate();
    }

    private List<BarEntry> createBarEntries(List<Object[]> data, String[] categories) {
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            float value = findValueForCategory(data, categories[i]);
            barEntries.add(new BarEntry(i, value));
        }
        return barEntries;
    }

    private List<Entry> createLineEntries(List<Object[]> data, String[] categories) {
        List<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            float value = findValueForCategory(data, categories[i]);
            lineEntries.add(new Entry(i, value));
        }
        return lineEntries;
    }

    private float findValueForCategory(List<Object[]> data, String category) {
        if (data == null) return 0f;
        for (Object[] entry : data) {
            if (category.equals(entry[0].toString())) {
                return Float.parseFloat(entry[1].toString());
            }
        }
        return 0f;
    }

    private BarDataSet createBarDataSet(List<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, "Total");
        dataSet.setColor(Color.parseColor("#0678BF"));
        dataSet.setDrawValues(false);
        return dataSet;
    }

    private LineDataSet createLineDataSet(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Manager");
        dataSet.setColor(Color.parseColor("#F28080"));
        dataSet.setCircleColor(Color.parseColor("#F28080"));
        dataSet.setCircleHoleColor(Color.parseColor("#F28080"));
        dataSet.setCircleRadius(6f);
        dataSet.setLineWidth(4f);
        dataSet.setDrawFilled(true);
        dataSet.setDrawValues(false);
        return dataSet;
    }

    private void configureChart(CombinedChart chart, CombinedData combinedData) {
        chart.setData(combinedData);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
    }

    private void updateRadarChart(Map<String, List<Object[]>> data) {
        if (data == null) {
            return;
        }

        List<String> categories = List.of("CAR", "HOT_ROLLED", "WIRE_ROD", "THICK_PLATE", "COLD_ROLLED");

        Map<String, Float> totalData = convertToDataMap(data.get("total"));
        Map<String, Float> managerData = convertToDataMap(data.get("manager"));

        List<RadarEntry> totalEntries = createRadarEntries(totalData, categories);
        List<RadarEntry> managerEntries = createRadarEntries(managerData, categories);

        RadarDataSet totalDataSet = createRadarDataSet(totalEntries, "Total", "#0678BF");
        RadarDataSet managerDataSet = createRadarDataSet(managerEntries, "Manager", "#F28080");

        RadarData radarData = new RadarData(totalDataSet, managerDataSet);
        configureRadarChart(radarChart, radarData);
    }

    private Map<String, Float> convertToDataMap(List<Object[]> dataArray) {
        Map<String, Float> dataMap = new HashMap<>();
        if (dataArray != null) {
            for (Object[] entry : dataArray) {
                String category = (String) entry[0];
                Float value = Float.parseFloat(entry[1].toString());
                dataMap.put(category, value);
            }
        }
        return dataMap;
    }

    private List<RadarEntry> createRadarEntries(Map<String, Float> data, List<String> categories) {
        List<RadarEntry> entries = new ArrayList<>();
        for (String category : categories) {
            float value = data.getOrDefault(category, 0.0f);
            entries.add(new RadarEntry(value));
        }
        return entries;
    }

    private RadarDataSet createRadarDataSet(List<RadarEntry> entries, String label, String colorHex) {
        RadarDataSet dataSet = new RadarDataSet(entries, label);
        int color = Color.parseColor(colorHex);
        dataSet.setColor(color);
        dataSet.setFillColor(color);
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(100);
        return dataSet;
    }

    private void configureRadarChart(RadarChart chart, RadarData radarData) {
        chart.setData(radarData);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(40f, 40f, 40f, 40f);
        chart.getYAxis().setDrawLabels(false);
        chart.getXAxis().setDrawLabels(true);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(List.of("자동차", "열연", "선재", "후판", "냉연")));
        chart.getXAxis().setTextSize(16f);
        chart.getLegend().setEnabled(false);
        radarData.setDrawValues(false);
        chart.invalidate();
    }

    private void setupXAxis(CombinedChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[] {
                "step 1", "step 2", "step 3", "step 4", "step 5", "step 6", "step 7"
        }));
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
