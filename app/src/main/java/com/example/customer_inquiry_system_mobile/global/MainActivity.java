package com.example.customer_inquiry_system_mobile.global;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.dashboard.fragment.DashboardFragment;
import com.example.customer_inquiry_system_mobile.domain.mypage.fragment.MypageFragment;
import com.example.customer_inquiry_system_mobile.domain.question.fragment.QuestionFragment;
import com.example.customer_inquiry_system_mobile.domain.inquiry.fragment.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final ListFragment fragmentList = new ListFragment();
    private final QuestionFragment fragmentQuestion = new QuestionFragment();
    private final DashboardFragment fragmentDashboard = new DashboardFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_frame_layout, fragmentList).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment selectedFragment = null;

            int itemId = menuItem.getItemId();
            if (itemId == R.id.fragment_list) {
                selectedFragment = fragmentList;
            } else if (itemId == R.id.fragment_question) {
                selectedFragment = fragmentQuestion;
            } else if (itemId == R.id.fragment_dashboard) {
                selectedFragment = fragmentDashboard;
            } else if (itemId == R.id.fragment_mypage) {
                // MypageFragment 생성 및 Bundle 설정
                MypageFragment fragmentMypage = new MypageFragment();
                Bundle bundle = new Bundle();

                // SharedPreferences에서 데이터 가져오기
                SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                String token = prefs.getString("token", null);
                Long userId = prefs.getLong("userId", -1);

                // 데이터가 유효한 경우에만 설정
                if (token != null && userId != -1) {
                    bundle.putString("token", token);
                    bundle.putLong("userId", userId);
                    fragmentMypage.setArguments(bundle);
                } else {
                    // 데이터가 유효하지 않은 경우 처리
                    Toast.makeText(MainActivity.this, "사용자 정보가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                }

                selectedFragment = fragmentMypage;
            }

            if (selectedFragment != null) {
                transaction.replace(R.id.menu_frame_layout, selectedFragment)
                        .commitAllowingStateLoss();
            }

            return true;
        }
    }
}
