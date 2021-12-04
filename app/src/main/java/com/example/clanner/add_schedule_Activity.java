package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class add_schedule_Activity extends AppCompatActivity {


    private Button addButton;
    private Switch alarmSwitch;
    private TextView selectTime;
    private EditText scheduleWriteArea;

    private Intent intent;
    private FirebaseUser FBuser;


    //파이어베이스의 DB에 접근하기 위한 작업
    private FirebaseDatabase FBdb  = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = FBdb.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        intent = getIntent();

        //xml컴포넌트 java객체 연결
        addButton = (Button) findViewById(R.id.addButton_addScheduleActivity);
        alarmSwitch = (Switch) findViewById(R.id.alarmSwitch_addScheduleActivity);
        selectTime = (TextView) findViewById(R.id.selectTimeText_addScheduleActivity);
        scheduleWriteArea = (EditText) findViewById(R.id.scheduleWriteArea_addScheduleActivity);

        //유저 구분하기 위한 현재 로그인 유저 확인
        FBuser = FirebaseAuth.getInstance().getCurrentUser();

        //버튼들에 clickListener 부착
        selectTime.setOnClickListener(listener);
        addButton.setOnClickListener(listener);



    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.addButton_addScheduleActivity :

                    int AlarmState;//알람 여부 변수
                    String content;
                    String time;

                    String today = intent.getStringExtra("날짜");

                    //알람 스위치가 켜져 있으면 1, 꺼져 있으면 0
                    if(alarmSwitch.isChecked()){ AlarmState = 1; } else{ AlarmState = 0; }

                    content = scheduleWriteArea.getText().toString();
                    time = selectTime.getText().toString();

                    //scheduleClass에 데이터 넣음
                    scheduleClass schedule = new scheduleClass(FBuser.getEmail(),content,AlarmState,time,today);

                    //schedule을 firebase에 입력하는 과정
                    if(!content.equals("")){
                        dbRef.child("user").child(FBuser.getUid()).child("schedule").push().setValue(schedule)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(add_schedule_Activity.this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(add_schedule_Activity.this, "저장하지 못했습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else{
                        Toast.makeText(getApplicationContext(),"일정을 입력하여 주십시오",Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.selectTimeText_addScheduleActivity :

                    TimePickerDialog TBdialog = new TimePickerDialog(add_schedule_Activity.this, android.R.style.Theme_Holo_Light_Dialog,TPlistenr,0,0,false);
                    TBdialog.setTitle("알람 시간 설정");
                    TBdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TBdialog.show();
                    break;
            }
        }
    };

    TimePickerDialog.OnTimeSetListener TPlistenr = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectTime.setText((hourOfDay + ":" + minute));
        }
    };

}