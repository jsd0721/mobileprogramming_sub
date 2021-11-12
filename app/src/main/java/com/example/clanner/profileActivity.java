package com.example.clanner;

import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class profileActivity extends AppCompatActivity {

    @Override
    protected  void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile3);

        findViewById(R.id.btnProfile).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = (v) -> {
        switch (v.getId()){
            case R.id.btnProfile:
                profile();
                break;
        }
    };

    private void  profile(){
        String name = ((EditText)findViewById(R.id.editUserName)).getText().toString();

        if (name.length() > 0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                    .build();

            if(user != null) {
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startToast("프로필 설정을 완료하였습니다.");
                                    finish();
                                }
                            }
                        });
            }

        }else {
            startToast("사용자 이름을 입력해 주세요.");
        }
    }
    private void  startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}






















