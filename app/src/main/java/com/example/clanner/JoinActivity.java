package com.example.clanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JoinActivity extends AppCompatActivity {
    private  static  final String TAG = "JoinActivity";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonJoin).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.buttonJoin:
                    join();
                    break;
            }
        }
    };
    private void  join(){
        String email = ((EditText)findViewById(R.id.editJoinEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.editJoinPassword)).getText().toString();

        if (email.length() > 0 && password.length() > 0){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, (task) ->  {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("회원가입에 성공하였습니다.");
                                startloginActivity();
                                // 성공했을때 로직
                            } else {
                                if (task.getException() != null){
                                    startToast(task.getException().toString());
                                }
                                // 실패했을때 로직
                            }
                    });
        }else {
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }
    }
    private void  startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void startloginActivity(){
        Intent intent = new Intent(this,loginActivity.class);
        startActivity(intent);
    }
}


























