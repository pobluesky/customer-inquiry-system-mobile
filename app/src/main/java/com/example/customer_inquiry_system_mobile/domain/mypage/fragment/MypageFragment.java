package com.example.customer_inquiry_system_mobile.domain.mypage.fragment;

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
import com.example.customer_inquiry_system_mobile.domain.mypage.api.UserApi;
import com.example.customer_inquiry_system_mobile.domain.mypage.dto.UserInfoResponseDTO;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageFragment extends Fragment {

    private UserApi userApi;

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
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        RetrofitService retrofitService = new RetrofitService();
        userApi = retrofitService.getRetrofit().create(UserApi.class);

        textViewName = view.findViewById(R.id.textViewNameValue);
        textViewEmail = view.findViewById(R.id.textViewEmailValue);
        textViewPhone = view.findViewById(R.id.textViewPhoneValue);
        textViewEmpNo = view.findViewById(R.id.textViewEmpNoValue);
        textViewRole = view.findViewById(R.id.textViewRoleValue);
        textViewDepartment = view.findViewById(R.id.textViewDepartmentValue);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String token = arguments.getString("token");
            Long userId = arguments.getLong("userId", -1);

            if (token != null && !token.isEmpty() && userId != -1) {
                fetchUserInfo(token, userId);
            } else {
                Toast.makeText(
                        getActivity(),
                        "유효하지 않은 사용자 정보입니다.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        } else {
            Toast.makeText(
                    getActivity(),
                    "유효하지 않은 사용자 정보입니다.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        return view;
    }


    private void fetchUserInfo(String token, Long userId) {
        Call<UserInfoResponseDTO> call = userApi.getUserInfo(token, userId);
        call.enqueue(new Callback<UserInfoResponseDTO>() {
            @Override
            public void onResponse(
                    @NonNull Call<UserInfoResponseDTO> call,
                    @NonNull Response<UserInfoResponseDTO> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    UserInfoResponseDTO userInfo = response.body();

                    textViewName.setText(userInfo.getName());
                    textViewEmail.setText(userInfo.getEmail());
                    textViewPhone.setText(userInfo.getPhone());
                    textViewEmpNo.setText(userInfo.getEmpNo());
                    textViewRole.setText(userInfo.getRole());
                    textViewDepartment.setText(userInfo.getDepartment());
                } else {
                    try {
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error";

                        Toast.makeText(
                                getActivity(),
                                "사용자 정보를 가져오지 못했습니다: " + errorBody,
                                Toast.LENGTH_SHORT
                        ).show();
                    } catch (IOException e) {
                        Log.e("UserInfoResponseDTO", "Error parsing error body", e);
                        Toast.makeText(
                                getActivity(),
                                "오류 발생: " + e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponseDTO> call, Throwable t) {
                Toast.makeText(
                        getActivity(),
                        "오류 발생: " + t.getMessage()
                        , Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
