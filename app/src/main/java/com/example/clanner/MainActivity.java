package com.example.clanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //xml상 컴포넌트랑 연결할 객체 선언
    Button scheduleAddBtn;
    MaterialCalendarView calendar;

    //날짜 저장할 문자열 변수 선언
    String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //객체와 xml상 컴포넌트 연결
        scheduleAddBtn = (Button)findViewById(R.id.schedule_add_button_mainActivity);
        calendar = (MaterialCalendarView)findViewById(R.id.calendarView_mainActivity);


        selectedDay = String.valueOf(calendar.getCurrentDate());
        calendar.setSelectedDate(CalendarDay.today());

        scheduleAddBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_scheduleAdd = new Intent(getApplicationContext(),add_schedule_Activity.class);
            startActivity(intent_scheduleAdd);

            }
        });
    }
}