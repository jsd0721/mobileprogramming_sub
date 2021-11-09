package com.example.clanner;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clanner.view.WriteInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// 다이어리 작성 부분에서 버튼 클릭했을때 발생하는 부분

public class diarywriteActivity extends AppCompatActivity {
    private static final String TAB = "diarywriteActivity";
    private FirebaseUser user;
    /*
    diaryDB helper;
    SQLiteDatabase db;
    EditText edtTitle,edtContent;
    */

    @Override
    protected void  onCreate(Bundle savedInsranceState) {
        super.onCreate(savedInsranceState);
        setContentView(R.layout.activity_diarywrite);

        findViewById(R.id.btnsave).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnsave:
                    //profile();
                    break;
            }

        }
    };
    /* 나중에 profile에 카메라 저장까지 만들고나면 작성해야함
    private void profile(){
        final String title = ((EditText)findViewById(R.id.edtTitle)).getText().toString();
        final String content = ((EditText)findViewById(R.id.edtContent)).getText().toString();

        if (title.length() > 0 && content.length() > 0){
            user = FirebaseUser.getInstance().getCurrentUser();
            WriteInfo writeInfo = new WriteInfo(title,content);
            uploader(writeInfo);
        }
        else{
            startToast("회원정보를 입력해 주세요.");
        }
    }

    private void uploader(WriteInfo writeInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.get)
    } */


        /* 요 부분은 패스
        edtContent=findViewById(R.id.edtContent);
        edtTitle=findViewById(R.id.edtTitle);

        helper = new diaryDB(this);
        db = helper.getWritableDatabase();

    }

    public void mClick(View v){
        switch (v.getId()){
            case  R.id.btncancle:
                finish();
                break;
            case R.id.btnsave:
                String strTitle = edtTitle.getText().toString();
                String strContent = edtContent.getText().toString();

                String sql = "insert into diary (subject,content) values(";
                sql += "'"+strTitle+"','"+strContent+"')";

                db.execSQL(sql);
               ///onRestart();
                finish();
                break;

        }*/

}
