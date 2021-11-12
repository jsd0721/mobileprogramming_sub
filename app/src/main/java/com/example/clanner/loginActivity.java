package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    /*
    private  static  final String TAG = "loginActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton_loginActivity).setOnClickListener(onClickListener);
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
                case  R.id.loginButton_loginActivity:
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
    } */
}














<<<<<<< HEAD
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

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_create_account = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent_create_account);
            }
        });
    }
}
=======
>>>>>>> ad95a50eb86ea44571670d39c7d93c056e9ba5d0
