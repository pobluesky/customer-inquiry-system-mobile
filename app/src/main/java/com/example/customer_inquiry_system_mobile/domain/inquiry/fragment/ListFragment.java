package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.customer_inquiry_system_mobile.domain.inquiry.adapter.InquiryAdapter;
import com.example.customer_inquiry_system_mobile.domain.inquiry.api.InquiryAPI;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.InquiryResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;

    private ImageView btnFilter;

    private View dimBackgroundView;

    private Spinner spinnerStatus;

    private Spinner spinnerProductType;

    private Spinner spinnerIndustryType;

    private Spinner spinnerInquiryType;

    public ListFragment() {}

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = rootView.findViewById(R.id.inquiryList_recyclerView);
        btnFilter = rootView.findViewById(R.id.btn_filter);

        spinnerStatus = rootView.findViewById(R.id.spinner_status);
        spinnerProductType = rootView.findViewById(R.id.spinner_product_type);
        spinnerIndustryType = rootView.findViewById(R.id.spinner_industry_type);
        spinnerInquiryType = rootView.findViewById(R.id.spinner_inquiry_type);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadInquiries();
        btnFilter.setOnClickListener(view -> showFilterPopup(view));

        return rootView;
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

        datePickerDialog.show();
    }

    private void setupSpinners() {
        setupSpinner(spinnerStatus, new String[]{
                "선택하세요.",
                "제출",
                "접수",
                "1차검토완료",
                "품질검토요청",
                "품질검토접수",
                "품질검토완료",
                "최종검토"
        }, "Status");

        setupSpinner(spinnerProductType, new String[]{
                "선택하세요.",
                "자동차",
                "기타",
                "건설",
                "유통",
                "전기",
                "가구",
                "도금",
                "고탄소",
                "주방",
                "저탄소",
                "기계",
                "파이프",
                "재압연",
                "조선",
                "운송",
                "선박",
                "빔"
        }, "Product Type");

        setupSpinner(spinnerIndustryType, new String[]{
                "선택하세요.",
                "냉연",
                "열연",
                "선재",
                "후판",
                "자동차"
        }, "Industry Type");

        setupSpinner(spinnerInquiryType, new String[]{
                "선택하세요.",
                "견적문의",
                "품질/견적문의"
        }, "Inquiry Type");
    }

    private void setupSpinner(Spinner spinner, String[] options, String label) {
        String[] updatedOptions = new String[options.length + 1];
        updatedOptions[0] = "";
        System.arraycopy(options, 0, updatedOptions, 1, options.length);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                options
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadInquiries() {
        RetrofitService retrofitService = new RetrofitService(null);
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        inquiryAPI.getAllInquiries().enqueue(new Callback<List<InquiryResponseDTO>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<InquiryResponseDTO>> call,
                    @NonNull Response<List<InquiryResponseDTO>> response
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
            public void onFailure(@NonNull Call<List<InquiryResponseDTO>> call, @NonNull Throwable t) {
                Toast.makeText(
                        getContext(),
                        "Failed to load inquiries",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void showFilterPopup(View anchorView) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View popupView = inflater.inflate(R.layout.filter_popup, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                1100,
                1800,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(anchorView, 0, 20);

        spinnerStatus = popupView.findViewById(R.id.spinner_status);
        spinnerProductType = popupView.findViewById(R.id.spinner_product_type);
        spinnerIndustryType = popupView.findViewById(R.id.spinner_industry_type);
        spinnerInquiryType = popupView.findViewById(R.id.spinner_inquiry_type);

        ImageView imgCalendarPopup = popupView.findViewById(R.id.img_calendar);
        ImageView imgCalendarPopupTo = popupView.findViewById(R.id.img_calendar_to);

        EditText editTextDatePopup = popupView.findViewById(R.id.editText_date);
        EditText editTextDatePopupTo = popupView.findViewById(R.id.editText_date_to);

        imgCalendarPopup.setOnClickListener(view -> showDatePicker(editTextDatePopup));
        imgCalendarPopupTo.setOnClickListener(view -> showDatePicker(editTextDatePopupTo));

        EditText editTextSeller = popupView.findViewById(R.id.editText_seller);
        EditText editTextCustomer = popupView.findViewById(R.id.editText_customer);
        EditText editTextSalesManager = popupView.findViewById(R.id.editText_sales_manager);
        EditText editTextQualityManager = popupView.findViewById(R.id.editText_quality_manager);

        setupSpinners();

        Button btnSearch = popupView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(v -> {
            String status = spinnerStatus.getSelectedItem() != null ?
                    spinnerStatus.getSelectedItem().toString() : "";

            String productType = spinnerProductType.getSelectedItem() != null ?
                    spinnerProductType.getSelectedItem().toString() : "";

            String industryType = spinnerIndustryType.getSelectedItem() != null ?
                    spinnerIndustryType.getSelectedItem().toString() : "";

            String inquiryType = spinnerInquiryType.getSelectedItem() != null ?
                    spinnerInquiryType.getSelectedItem().toString() : "";

            String dateFrom = editTextDatePopup.getText().toString();
            String dateTo = editTextDatePopupTo.getText().toString();
            String seller = editTextSeller.getText().toString();
            String customer = editTextCustomer.getText().toString();
            String salesManager = editTextSalesManager.getText().toString();
            String qualityManager = editTextQualityManager.getText().toString();

            searchInquiries(
                    status,
                    productType,
                    industryType,
                    inquiryType,
                    dateFrom,
                    dateTo,
                    seller,
                    customer,
                    salesManager,
                    qualityManager
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

    private void searchInquiries(
            String status,
            String productType,
            String industryType,
            String inquiryType,
            String dateFrom,
            String dateTo,
            String seller,
            String customer,
            String salesManager,
            String qualityManager
    ) {
        RetrofitService retrofitService = new RetrofitService(null);
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        Map<String, String> queryMap = new HashMap<>();

        if (!status.isEmpty() && !status.equals("선택하세요."))
            queryMap.put("progress", status);

        if (!productType.isEmpty() && !productType.equals("선택하세요."))
            queryMap.put("productType", productType);

        if (!industryType.isEmpty() && !industryType.equals("선택하세요."))
            queryMap.put("industry", industryType);

        if (!inquiryType.isEmpty() && !inquiryType.equals("선택하세요."))
            queryMap.put("inquiryType", inquiryType);

        if (!dateFrom.isEmpty())
            queryMap.put("startDate", dateFrom);

        if (!dateTo.isEmpty())
            queryMap.put("endDate", dateTo);

        if (!seller.isEmpty())
            queryMap.put("salesPerson", seller);

        if (!customer.isEmpty())
            queryMap.put("customerName", customer);

        if (!salesManager.isEmpty())
            queryMap.put("salesManagerName", salesManager);

        if (!qualityManager.isEmpty())
            queryMap.put("qualityManagerName", qualityManager);


        inquiryAPI.getInquiriesBySearch("LATEST", queryMap)
                .enqueue(new Callback<List<InquiryResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<InquiryResponseDTO>> call,
                                   @NonNull Response<List<InquiryResponseDTO>> response) {
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
            public void onFailure(@NonNull Call<List<InquiryResponseDTO>> call, @NonNull Throwable t) {
                Toast.makeText(
                        getContext(),
                        "Failed to load inquiries",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
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

    private void populateListView(List<InquiryResponseDTO> inquiryResponseDTOList) {
        InquiryAdapter inquiryAdapter = new InquiryAdapter(inquiryResponseDTOList, getContext());
        recyclerView.setAdapter(inquiryAdapter);
    }
}
