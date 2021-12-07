package com.example.clanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class listOfFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_friends);

        Toolbar Tb = findViewById(R.id.Toolbar_listOfFriendsActivity);

        setSupportActionBar(Tb);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("친구 관리");

    }
}