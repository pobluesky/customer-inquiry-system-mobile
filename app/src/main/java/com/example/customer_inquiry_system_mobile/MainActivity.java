package com.example.customer_inquiry_system_mobile;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

            int itemId = menuItem.getItemId();

            if (itemId == R.id.fragment_list) {
                transaction.replace(R.id.menu_frame_layout, fragmentList)
                        .commitAllowingStateLoss();
            } else if (itemId == R.id.fragment_question) {
                transaction.replace(R.id.menu_frame_layout, fragmentQuestion)
                        .commitAllowingStateLoss();
            } else if (itemId == R.id.fragment_dashboard) {
                transaction.replace(R.id.menu_frame_layout, fragmentDashboard)
                        .commitAllowingStateLoss();
            }

            return true;
        }
    }
}
