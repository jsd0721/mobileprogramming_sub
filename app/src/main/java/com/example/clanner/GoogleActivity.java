package com.example.clanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class GoogleActivity extends AppCompatActivity {

    private TextView txt_google; //닉네임 TEXT
    private ImageView profile_google; // 프로필 IMAGEVIEW
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName"); // loginActivity로 부터 닉네임 전달받음
        String photoUrl = intent.getStringExtra("photoUrl");

        txt_google = findViewById(R.id.txt_google);
        txt_google.setText(nickName);

        profile_google = findViewById(R.id.profile_google);
        Glide.with(this).load(photoUrl).into(profile_google); // 프로필 url을 이미지뷰에 세팅


        findViewById(R.id.googleOk_btn).setOnClickListener(onClickListener); //로그인버튼
        findViewById(R.id.googleNo_btn).setOnClickListener(onClickListener); //가입하기 textview
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.googleOk_btn:
                    startMainActivity();
                    startToast("로그인을 성공하였습니다.");
                    break;
                case R.id.googleNo_btn:
                    startloginActivity();
                    break;
            }
        }
    };
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startloginActivity() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}
