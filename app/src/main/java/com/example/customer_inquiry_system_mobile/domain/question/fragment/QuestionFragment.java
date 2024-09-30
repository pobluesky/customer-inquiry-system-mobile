package com.example.customer_inquiry_system_mobile.domain.question.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.question.adapter.QuestionAdapter;
import com.example.customer_inquiry_system_mobile.domain.question.api.QuestionAPI;
import com.example.customer_inquiry_system_mobile.domain.question.dto.QuestionResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionFragment extends Fragment {

    private RecyclerView recyclerView;

    private ImageView btnFilter;

    private View dimBackgroundView;

    private Spinner spinnerStatus;

    private Spinner spinnerQuestionType;

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        recyclerView = rootView.findViewById(R.id.questionList_recyclerView);
        btnFilter = rootView.findViewById(R.id.btn_filter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadQuestions();
        btnFilter.setOnClickListener(view -> showFilterPopup(view));

        return rootView;
    }

    private void loadQuestions() {
        RetrofitService retrofitService = new RetrofitService(null);
        QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);

        questionAPI.getAllQuestions().enqueue(new Callback<List<QuestionResponseDTO>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<QuestionResponseDTO>> call,
                    @NonNull Response<List<QuestionResponseDTO>> response
            ) {
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
            public void onFailure(
                    @NonNull Call<List<QuestionResponseDTO>> call,
                    @NonNull Throwable t
            ) {
                Toast.makeText(
                        getContext(),
                        "Failed to load questions",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void showFilterPopup(View anchorView) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View popupView = inflater.inflate(R.layout.filter_popup_question, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                1100,
                1200,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(anchorView, 0, 20);

        spinnerStatus = popupView.findViewById(R.id.spinner_status);
        spinnerQuestionType = popupView.findViewById(R.id.spinner_question_type);

        ImageView imgCalendarPopup = popupView.findViewById(R.id.img_calendar);
        ImageView imgCalendarPopupTo = popupView.findViewById(R.id.img_calendar_to);

        EditText editTextDatePopup = popupView.findViewById(R.id.editText_date);
        EditText editTextDatePopupTo = popupView.findViewById(R.id.editText_date_to);

        imgCalendarPopup.setOnClickListener(view -> showDatePicker(editTextDatePopup));
        imgCalendarPopupTo.setOnClickListener(view -> showDatePicker(editTextDatePopupTo));

        EditText editTextTitle = popupView.findViewById(R.id.editText_title);
        EditText editTextCustomer = popupView.findViewById(R.id.editText_customer);

        setupSpinners();

        Button btnSearch = popupView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(v -> {
            String status = spinnerStatus.getSelectedItem() != null ?
                    spinnerStatus.getSelectedItem().toString() : "";
            String questionType = spinnerQuestionType.getSelectedItem() != null ?
                    spinnerQuestionType.getSelectedItem().toString() : "";
            String dateFrom = editTextDatePopup.getText().toString();
            String dateTo = editTextDatePopupTo.getText().toString();
            String title = editTextTitle.getText().toString();
            String customer = editTextCustomer.getText().toString();

            searchQuestions(
                    status,
                    questionType,
                    title,
                    customer,
                    dateFrom,
                    dateTo
            );

            popupWindow.dismiss();
        });

        ImageView imgClose = popupView.findViewById(R.id.img_close);
        imgClose.setOnClickListener(view -> {
            popupWindow.dismiss();
        });

        setDimBackground(true);
        popupWindow.setOnDismissListener(() -> setDimBackground(false));
    }

    private void searchQuestions(
            String status,
            String questionType,
            String title,
            String customer,
            String dateFrom,
            String dateTo
    ) {
        RetrofitService retrofitService = new RetrofitService(null);
        QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);

        Map<String, String> queryMap = new HashMap<>();

        if (!status.isEmpty() && !status.equals("선택하세요."))
            queryMap.put("status", status);

        if (!questionType.isEmpty() && !questionType.equals("선택하세요."))
            queryMap.put("type", questionType);

        if (!dateFrom.isEmpty())
            queryMap.put("startDate", dateFrom);

        if (!dateTo.isEmpty())
            queryMap.put("endDate", dateTo);

        if (!title.isEmpty())
            queryMap.put("title", title);

        if (!customer.isEmpty())
            queryMap.put("customerName", customer);


        questionAPI.getQuestionsBySearch("LATEST",queryMap)
                .enqueue(new Callback<List<QuestionResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<QuestionResponseDTO>> call,
                                   @NonNull Response<List<QuestionResponseDTO>> response) {
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
            public void onFailure(
                    @NonNull Call<List<QuestionResponseDTO>> call,
                    @NonNull Throwable t
            ) {
                Toast.makeText(
                        getContext(),
                        "Failed to load questions",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void setupSpinners() {
        setupSpinner(spinnerStatus, new String[]{
                "선택하세요.",
                "답변대기",
                "답변완료",
        });

        setupSpinner(spinnerQuestionType, new String[]{
                "선택하세요.",
                "주문문의",
                "사이트이용문의",
                "기타문의"
        });
    }

    private void setupSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void populateListView(List<QuestionResponseDTO> questionResponseDTOList) {
        QuestionAdapter questionAdapter = new QuestionAdapter(questionResponseDTOList, getContext());
        recyclerView.setAdapter(questionAdapter);
    }

    private void setDimBackground(boolean show) {
        if (show) {
            if (dimBackgroundView == null) {
                dimBackgroundView = new View(getContext());
                dimBackgroundView.setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                dimBackgroundView.setBackgroundColor(0x88000000);

                ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
                rootView.addView(dimBackgroundView);
            }
            dimBackgroundView.setVisibility(View.VISIBLE);
        } else {
            if (dimBackgroundView != null) {
                dimBackgroundView.setVisibility(View.GONE);
            }
        }
    }

    private void showDatePicker(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String selectedDate = String.format(
                            "%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                    );

                    editText.setText(selectedDate);
                },
                year, month, day
        );

        // DatePickerDialog 띄우기
        datePickerDialog.show();
    }
}
