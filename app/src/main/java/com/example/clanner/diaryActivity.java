package com.example.clanner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class diaryActivity extends AppCompatActivity {
    diaryDB helper;
    SQLiteDatabase db;
    Myadapter adapter; // 데이터를 listview로 출력하게 연결
    ListView list; // 데이터 출력
    Cursor cursor; // 출력할 데이터를 담아줌

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        helper = new diaryDB(this); // 현재 위치에 diaryDB라는 데이터베이스 생성
        db=helper.getReadableDatabase(); // 새로 만든 diaryDB라는 데이터를 읽기용으로 가져옴
        cursor = db.rawQuery("select * from diary order by wdate desc", null); // create될때 커서 생성해서 sql문 작성, sql 문으로 실행 할 데이터 커서에 삽입

        list=findViewById(R.id.list);
        adapter=new Myadapter(diaryActivity.this,cursor);
        list.setAdapter(adapter);

        //아래는는 activiy_diary에 작성버튼 클릭하면 activity_diarywrite 가 나오도록 하는 부분
        Button btnDiarywrite = findViewById(R.id.btnDiarywrite);
        btnDiarywrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(diaryActivity.this, diarywriteActivity.class); //메인에서 write로 이동
                startActivity(intent); //이동 실행
            }
        });
    }

    //커서 어댑터를 상속받아 어댑터 생성성
   public class  Myadapter extends CursorAdapter{

        public Myadapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return getLayoutInflater().inflate(R.layout.diary_item, parent,false);
            //Adapter의 new view 에서 인플레이터를 이용해 item.xml을 가져와서 적용
            //커서의 데이터 갯수만큼 item.xml 형식으로 데이터 출력
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView textTitle = view.findViewById(R.id.textTitle); //id가 textTitle이라는 형식의 텍스트박스 가져와 TextView 생성
            textTitle.setText(cursor.getString(1)); // 데이터베이스에서 불러온 데이터가 있는 cursor의 2번째 데이터를 textTtile에 생성

        }
    }

    @Override
    protected void onRestart() {
        cursor = db.rawQuery("select * from diary order by wdate desc", null);
        adapter.changeCursor(cursor);
        super.onRestart();
    }
}
