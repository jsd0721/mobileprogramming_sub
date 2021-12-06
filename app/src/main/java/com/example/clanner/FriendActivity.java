package com.example.clanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        ListView listview = findViewById(R.id.menuList_FriendActivity);
        String[] array = {"친구 추가","친구 삭제", "일정 공유 설정"};
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,array);
        listview.setAdapter(adapter);


    }
}