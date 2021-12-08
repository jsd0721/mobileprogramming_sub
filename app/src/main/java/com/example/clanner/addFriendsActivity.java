package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addFriendsActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference DBReference;
    String key;
    String Data;
    ArrayList<String> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        auth = FirebaseAuth.getInstance();
        String key;
        Button addFriendsBtn = findViewById(R.id.friendAddButton_addFriendsActivity);

        addFriendsBtn.setOnClickListener(addBtnClickListener);

    }

    View.OnClickListener addBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            EditText EmailText = findViewById(R.id.insertEmail_addFriendsActivity);
            String email = EmailText.getText().toString();
            //user 정보 불러오기
            FirebaseUser user = auth.getCurrentUser();
            if(email.equals("")||email.equals(user.getEmail())){
                Toast.makeText(addFriendsActivity.this,"이메일을 확인하여 주세요",Toast.LENGTH_SHORT).show();
            }else{
                //firebase realtime database 연결
                DBReference = FirebaseDatabase.getInstance().getReference();



                //프렌즈 노드에 액세스 하기 위한 코드
                DatabaseReference userReference = DBReference.child("user");
                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data : snapshot.getChildren()) {
                            key = data.getKey();
                            Data = snapshot.child(key).child("user_info").child("user_email").getValue().toString();
                            Log.d("데이터", Data);
                            if (email.equals(Data)) {

                                //친구 신청을 하는 과정(세 가지 상태가 있음-stay : 수락 대기, reject - 수락 거절, allow - 수락 되어있음)
                                Map<String,String> send_addFriend = new HashMap<>();
                                send_addFriend.put("status","stay");
                                send_addFriend.put("email",user.getEmail());
                                send_addFriend.put("user_nickname",snapshot.child(user.getUid()).child("user_info").child("user_nickname").getValue().toString());
                                send_addFriend.put("user_photo",snapshot.child(user.getUid()).child("user_info").child("user_photo").getValue().toString());


                                userReference.child(key).child("friends").child(user.getUid()).setValue(send_addFriend);
                                Toast.makeText(addFriendsActivity.this, "친구 신청이 성공적으로 완료되었습니다", Toast.LENGTH_SHORT).show();
                                EmailText.setText("");
                            }else{
                                Toast.makeText(addFriendsActivity.this,"사용자를 찾을 수 없습니다",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    };
}