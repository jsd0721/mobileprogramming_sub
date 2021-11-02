package com.example.clanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    Button loginBtn;

    TextView createAccount;
    TextView findAccount;
    TextView warningText;

    EditText idInput;
    EditText passwordInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.loginButton_loginActivity);
        createAccount = (TextView) findViewById(R.id.createAccount_loginActivity);
        findAccount = (TextView)findViewById(R.id.ID_passwordFind_loginActivity);
        warningText = (TextView)findViewById(R.id.warningText_loginActivity);
        idInput = (EditText)findViewById(R.id.IDInput_loginActivity);
        passwordInput = (EditText)findViewById(R.id.passwordInput_loginActivity);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,MainActivity.class);

                //아이디랑 패스워드 확인 후 일치하면 넘어가고, 아니면 안 넘어가는 메소드
                String id = idInput.getText().toString();
                String password = passwordInput.getText().toString();

                if(id.equals("")|| password.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else {


                    if (id.equals("jsd0721") && password.equals("ZEUS0721")) {
                        warningText.setVisibility(View.INVISIBLE);
                        Intent intent1 = intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                        finish();
                    } else {
                        warningText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}