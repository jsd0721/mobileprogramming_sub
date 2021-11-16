package com.example.clanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class add_schedule_Activity extends AppCompatActivity {


    Button addbutton;
    Switch alarmSwitch;
    TextView selectTime;
    EditText scheduleWriteArea;

    private FirebaseDatabase FBdb  = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = FBdb.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Intent intent = getIntent();

        addbutton = (Button) findViewById(R.id.addButton_addScheduleActivity);
        alarmSwitch = (Switch) findViewById(R.id.alarmSwitch_addScheduleActivity);
        selectTime = (TextView) findViewById(R.id.selectTimeText_addScheduleActivity);
        scheduleWriteArea = (EditText) findViewById(R.id.scheduleWriteArea_addScheduleActivity);


        FirebaseUser FBuser = FirebaseAuth.getInstance().getCurrentUser();


        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(FBuser != null){
            Log.d("현재 로그인 유저",FBuser.getEmail());
        }else{
            Log.d("현재 로그인 유저","없습니다.");
        }


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int AlarmState;//알람 여부 변수
                String content;
                String time;
                String today = intent.getStringExtra("날짜");

                //알람 스위치가 켜져 있으면 1, 꺼져 있으면 0
                if(alarmSwitch.isChecked()){ AlarmState = 1; } else{ AlarmState = 0; }

                content = scheduleWriteArea.getText().toString();
                time = selectTime.getText().toString();



                scheduleClass schedule = new scheduleClass(FBuser.getUid(),content,AlarmState,"15:30");
                dbRef.child("day = " + today ).child("Schedule").setValue(schedule);
                Toast.makeText(add_schedule_Activity.this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }


}