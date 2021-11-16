package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class loginActivity extends AppCompatActivity {

    private static final String TAG = "loginActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton_loginActivity).setOnClickListener(onClickListener); //로그인버튼
        findViewById(R.id.createAccount_loginActivity).setOnClickListener(onClickListener); //가입하기 textview
        findViewById(R.id.passwordFind_loginActivity).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginButton_loginActivity:
                    login();
                    break;
                case R.id.createAccount_loginActivity:
                    startjoinActivity();
                    break;
                case R.id.passwordFind_loginActivity:
                    startPasswordResetActivity();
                    break;
            }

        }
    };

    private void login() {
        String email = ((EditText) findViewById(R.id.EmailInput_loginActivity)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordInput_loginActivity)).getText().toString();

        if (email.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인에 성공하였습니다.");
                                startMainActivity();
                            } else {
                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }
                        }
                    });
        } else {
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startjoinActivity() {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    private void startPasswordResetActivity() {
        Intent intent = new Intent(this, PasswordResetActivity.class);
        startActivity(intent);
    }
}

