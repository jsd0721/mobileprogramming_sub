package com.example.clanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        ListView listview = findViewById(R.id.menuList_FriendActivity);
        String[] array = {"친구 목록","친구 추가", "일정 공유 설정"};
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,array);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
//                    Intent intent = new Intent(FriendActivity.this,listOfFriendsActivity.class);
//                    startActivity(intent);
                }else if(i == 1){
                    Intent intent = new Intent(FriendActivity.this,addFriendsActivity.class);
                    startActivity(intent);
                }else{
//                    Intent intent = new Intent(FriendActivity.this,scheduleShareActivity.class);
//                    startActivity(intent);
                }
            }
        });


    }
}