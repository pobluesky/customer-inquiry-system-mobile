package com.example.customer_inquiry_system_mobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import com.example.customer_inquiry_system_mobile.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ActivityMainBinding을 초기화
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // RecyclerView에 표시할 데이터 생성
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person(R.drawable.question, "오제이", "010-1234-1234"));
        list.add(new Person(R.drawable.question, "홍길동", "010-1111-2222"));
        list.add(new Person(R.drawable.question, "마이네임", "010-0000-0000"));

        // RecyclerViewAdapter 초기화
        recyclerViewAdapter = new RecyclerViewAdapter(list);

        // RecyclerView 설정
        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.mainRecyclerview.setAdapter(recyclerViewAdapter);
    }
}
