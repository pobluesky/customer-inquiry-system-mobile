package com.example.customer_inquiry_system_mobile.global;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.dashboard.fragment.DashboardFragment;
import com.example.customer_inquiry_system_mobile.domain.mypage.fragment.MypageFragment;
import com.example.customer_inquiry_system_mobile.domain.notification.NotificationActivity;
import com.example.customer_inquiry_system_mobile.domain.notification.NotificationHelper;
import com.example.customer_inquiry_system_mobile.domain.question.fragment.QuestionFragment;
import com.example.customer_inquiry_system_mobile.domain.inquiry.fragment.ListFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;


import android.Manifest;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private final ListFragment fragmentList = new ListFragment();

    private final QuestionFragment fragmentQuestion = new QuestionFragment();

    private final DashboardFragment fragmentDashboard = new DashboardFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }

        ImageView bellIcon = findViewById(R.id.bell);

        bellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_frame_layout, fragmentList).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        Intent intent = getIntent();

        if(intent != null) {
            String notificationData = intent.getStringExtra("test");
            if(notificationData != null)
                Log.d("FCM_TEST", notificationData);
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        String token = task.getResult();
                        Log.d("token",token);
                        System.out.println(token);
                        Toast.makeText(
                                MainActivity.this,
                                "Your device registration token is" + token,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        NotificationHelper.createNotificationChannels(this);
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            Toast.makeText(
                            this,
                                    "Notification permission granted",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            Toast.makeText(
                                    this,
                                    "Notification permission denied",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });

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
                MypageFragment fragmentMypage = new MypageFragment();
                Bundle bundle = new Bundle();

                SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);

                String token = prefs.getString("token", null);
                Long userId = prefs.getLong("userId", -1);

                if (token != null && userId != -1) {
                    bundle.putString("token", token);
                    bundle.putLong("userId", userId);

                    fragmentMypage.setArguments(bundle);
                } else {
                    Toast.makeText(
                            MainActivity.this,
                            "사용자 정보가 유효하지 않습니다.",
                            Toast.LENGTH_SHORT
                    ).show();
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
