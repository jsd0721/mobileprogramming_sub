package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    //xml상 컴포넌트랑 연결할 객체 선언
    private FloatingActionButton scheduleAddBtn;
    private MaterialCalendarView calendar;
    private RecyclerView recyclerView;
    private Toolbar tb;
    private ArrayList list;
    private ListView menuList;

    //리사이클러뷰 연결하기 위한 객체 선언
    private RecyclerView.LayoutManager LNmanager;
    private scheduleRCViewAdapter RCViewAdapter;

    //날짜 저장할 문자열 변수 선언
    private String selectedDay;

    //파이어베이스 데이터베이스 관련
    FirebaseDatabase FBdb = FirebaseDatabase.getInstance();
    DatabaseReference DBReference = FBdb.getReference();
    FirebaseUser nowUser = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //realtimeDB 데이터 받아올 리스트 어레이리스트 선언
        list = new ArrayList<scheduleClass>();

        //메뉴 화면에 표시해 줄 리스트 텍스트 선언
        String[] listMenuTitle = {"내가 쓴 다이어리","친구 관리","설정"};

        //객체와 xml상 컴포넌트 연결
        scheduleAddBtn = (FloatingActionButton)findViewById(R.id.scheduleWriteButton_mainActivity);
        calendar = (MaterialCalendarView)findViewById(R.id.calendarView_mainActivity);
        tb = (Toolbar)findViewById(R.id.toolbar_mainactivity);
        recyclerView = (RecyclerView)findViewById(R.id.scheduleRCView_mainActivity);
        menuList = (ListView)findViewById(R.id.menuList_MainActivity);


        //액션바 설정
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("");

        //액션바에서 내비게이션 버튼 만들기
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.menuicon);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listMenuTitle);
        menuList.setAdapter(adapter);

        //달력 설정. UI상의 달력에서 선택 날짜를 오늘로 변경하고, selectedday 변수에 일정 추가 액티비티에 넘겨줄 선택 날짜 저장
        calendar.setSelectedDate(CalendarDay.today());
        calendar.setOnDateChangedListener(onDateSelectedListener);

        getData();


        scheduleAddBtn.setOnClickListener(clickListener);

        //리사이클러뷰에 레이아웃 매니저와 어댑터 부착
        LNmanager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        RCViewAdapter = new scheduleRCViewAdapter(list,this);
        recyclerView.setLayoutManager(LNmanager);
        recyclerView.setAdapter(RCViewAdapter);


    }

    //뒤로가기 버튼을 눌렀을 때 종료
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
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_scheduleAdd = new Intent(getApplicationContext(),add_schedule_Activity.class);
            intent_scheduleAdd.putExtra("날짜",selectedDay);
            startActivity(intent_scheduleAdd);

        }
    };
    OnDateSelectedListener onDateSelectedListener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            selectedDay = String.valueOf(widget.getSelectedDate().getYear()) + "-"
                    + String.valueOf(widget.getSelectedDate().getMonth()+1) + "-"
                    + String.valueOf(widget.getSelectedDate().getDay());
            Log.d("선택된 시간",selectedDay);
        }
    };
    private void getData(){
        if(DBReference.child("user").child(nowUser.getUid()).child(selectedDay).getKey() != null) {
            DBReference.child("user").child(nowUser.getUid()).child(selectedDay).addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    scheduleClass schedule_inGetData = snapshot.getValue(scheduleClass.class);

                    String user = schedule_inGetData.user;
                    String content = schedule_inGetData.content;
                    int alarmState = schedule_inGetData.alarmState;
                    String time = schedule_inGetData.time;

                    Log.d("alarmstate", content);
                    list.add(schedule_inGetData);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    list.add(new scheduleClass("데이터가 없습니다", "0", 1, "0"));
                }

            });
        }else{
            list.add(new scheduleClass("데이터가 없습니다","없습니다",1,"data not found"));
        }
    }

}