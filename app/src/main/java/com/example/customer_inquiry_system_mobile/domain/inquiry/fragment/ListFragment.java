package com.example.customer_inquiry_system_mobile.domain.inquiry.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.List;

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

        // Spinner 초기화
        spinnerStatus = rootView.findViewById(R.id.spinner_status);
        spinnerProductType = rootView.findViewById(R.id.spinner_product_type);
        spinnerIndustryType = rootView.findViewById(R.id.spinner_industry_type);
        spinnerInquiryType = rootView.findViewById(R.id.spinner_inquiry_type);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadInquiries();

        btnFilter.setOnClickListener(view -> showFilterPopup(view));

        return rootView;
    }

    // 4개의 Spinner 설정 메서드
    private void setupSpinners() {
        // 상태 필터 스피너 설정
        setupSpinner(spinnerStatus, new String[]{
                "제출",
                "접수",
                "1차검토완료",
                "품질검토요청",
                "품질검토접수",
                "품질검토완료",
                "최종검토"
        }, "Status");

        // 산업 유형 스피너 설정
        setupSpinner(spinnerProductType, new String[]{
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

        // 제품 유형 스피너 설정
        setupSpinner(spinnerIndustryType, new String[]{
                "냉연",
                "열연",
                "선재",
                "후판",
                "자동차"
        }, "Industry Type");

        // 문의 유형 스피너 설정
        setupSpinner(spinnerInquiryType, new String[]{"견적문의", "품질/견적문의"}, "Inquiry Type");
    }

    // 개별 Spinner 설정 메서드
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
                Toast.makeText(getContext(), label + " Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
                // 선택된 옵션에 따라 필터링 로직 추가 가능
                // 예: filterInquiriesByStatus(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무 항목도 선택되지 않았을 때 처리
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
                2000,
                true
        );

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(anchorView, 0, 20);

        // 필터 팝업에 있는 Spinner 초기화 및 설정
        spinnerStatus = popupView.findViewById(R.id.spinner_status);
        spinnerProductType = popupView.findViewById(R.id.spinner_product_type);
        spinnerIndustryType = popupView.findViewById(R.id.spinner_industry_type);
        spinnerInquiryType = popupView.findViewById(R.id.spinner_inquiry_type);

        // Spinner 설정 메서드 호출
        setupSpinners();

        // 검색 버튼 클릭 리스너 설정
        Button btnSearch = popupView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(v -> {
            // 선택된 필터 값 가져오기
            String status = spinnerStatus.getSelectedItem() != null ? spinnerStatus.getSelectedItem().toString() : "";
            String productType = spinnerProductType.getSelectedItem() != null ? spinnerProductType.getSelectedItem().toString() : "";
            String industryType = spinnerIndustryType.getSelectedItem() != null ? spinnerIndustryType.getSelectedItem().toString() : "";
            String inquiryType = spinnerInquiryType.getSelectedItem() != null ? spinnerInquiryType.getSelectedItem().toString() : "";

            // API 호출
            searchInquiries(status, productType, industryType, inquiryType);
            popupWindow.dismiss(); // 팝업 닫기
        });

        setDimBackground(true);
        popupWindow.setOnDismissListener(() -> setDimBackground(false));
    }

    private void searchInquiries(String status, String productType, String industryType, String inquiryType) {
        RetrofitService retrofitService = new RetrofitService(null);
        InquiryAPI inquiryAPI = retrofitService.getRetrofit().create(InquiryAPI.class);

        // 필터가 선택되지 않았을 경우 모든 문의 가져오기
        if (status.isEmpty() && productType.isEmpty() && industryType.isEmpty() && inquiryType.isEmpty()) {
            loadInquiries(); // 기본 메서드 호출
        } else {
            // API 호출
            inquiryAPI.getInquiriesBySearch("LATEST", status, productType, null, inquiryType, null, industryType, null, null, null, null)
                    .enqueue(new Callback<List<InquiryResponseDTO>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<InquiryResponseDTO>> call,
                                               @NonNull Response<List<InquiryResponseDTO>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                populateListView(response.body());
                            } else {
                                Toast.makeText(getContext(), "Failed to load inquiries: " + response.message(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<InquiryResponseDTO>> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Failed to load inquiries", Toast.LENGTH_LONG).show();
                        }
                    });
        }
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
