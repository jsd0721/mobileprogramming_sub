package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


public class MainActivity extends AppCompatActivity {

    //xml상 컴포넌트랑 연결할 객체 선언
    Button scheduleAddBtn;
    MaterialCalendarView calendar;
    Toolbar tb;

    //날짜 저장할 문자열 변수 선언
    String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //객체와 xml상 컴포넌트 연결
        scheduleAddBtn = (Button)findViewById(R.id.schedule_add_button_mainActivity);
        calendar = (MaterialCalendarView)findViewById(R.id.calendarView_mainActivity);
        tb = (Toolbar)findViewById(R.id.toolbar_mainactivity);

        //액션바 설정
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("");

        //액션바에서 내비게이션 버튼 만들기
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.menuicon);





        calendar.setSelectedDate(CalendarDay.today());


        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDay = String.valueOf(date).substring(12,22);
                Log.d("선택된 시간",selectedDay);
            }
        });


        scheduleAddBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_scheduleAdd = new Intent(getApplicationContext(),add_schedule_Activity.class);
            intent_scheduleAdd.putExtra("날짜",selectedDay);
            startActivity(intent_scheduleAdd);

            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("클래너")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

}