package com.example.customer_inquiry_system_mobile.domain.mypage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.mypage.api.UserApi;
import com.example.customer_inquiry_system_mobile.domain.mypage.dto.LoginRequestDTO;
import com.example.customer_inquiry_system_mobile.domain.mypage.dto.LoginResponseDTO;
import com.example.customer_inquiry_system_mobile.global.MainActivity;
import com.example.customer_inquiry_system_mobile.global.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RetrofitService retrofitService = new RetrofitService();
        userApi = retrofitService.getUserApi();

        EditText editTextId = findViewById(R.id.editTextId);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    login(email, password);
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "아이디와 비밀번호를 입력하세요.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void login(String email, String password) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
        Call<LoginResponseDTO> call = userApi.signIn(loginRequestDTO);

        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String accessToken = response.body().getAccessToken();
                    Long userId = response.body().getUserId();

                    SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", accessToken);
                    editor.putLong("userId", userId);
                    editor.apply();

                    // MainActivity로 이동
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "로그인 실패",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Toast.makeText(
                        LoginActivity.this,
                        "로그인 오류: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

}
