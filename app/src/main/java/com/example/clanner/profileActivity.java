package com.example.clanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class profileActivity extends AppCompatActivity {
    private  static  final String TAG = "profileActivity";
    private ImageView imageView3;
    private FirebaseUser user;
    private String profilePath;

    @Override
    protected  void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(onClickListener);

        findViewById(R.id.btnProfile).setOnClickListener(onClickListener);
        findViewById(R.id.picture).setOnClickListener(onClickListener);
        findViewById(R.id.gallery).setOnClickListener(onClickListener);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 0:{
                if (resultCode == Activity.RESULT_OK){
                    profilePath = data.getStringExtra("profilePath");
                    Bitmap bmp = BitmapFactory.decodeFile(profilePath);
                    imageView3.setImageBitmap(bmp);
                }
                break;
            }
        }
    }


    View.OnClickListener onClickListener = (v) -> {
        switch (v.getId()) {
            case R.id.btnProfile:
                profile();
                break;
            case R.id.imageView3:
                CardView cardView = findViewById(R.id.buttonsCardView);
                if (cardView.getVisibility() == View.VISIBLE){
                    cardView.setVisibility(View.GONE);
                }else{
                    cardView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.picture:
                myStartActivity(CameraActivity.class);
                break;
            case R.id.gallery:
                if (ContextCompat.checkSelfPermission(profileActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(profileActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    if (ActivityCompat.shouldShowRequestPermissionRationale(profileActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    }else {
                        startToast("권한을 허용해 주세요.");
                    }
                }else{
                    myStartActivity(GalleryActivity.class);
                }
                break;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myStartActivity(GalleryActivity.class);
                } else {
                    startToast("권한을 허용해 주세요.");
                }
            }
        }
    }


    private void  profile(){
        final String name = ((EditText)findViewById(R.id.editUserName)).getText().toString();

        if (name.length() > 0){

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            /* 사용자  URL 가져오는 부분 */
            user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImagesRef = storageRef.child("users/"+user.getUid()+"/profileImage.jpg");

            /* null 이면 정보만 보내고 아니면 photourl까지 같이 보내는 부분 */
            if (profilePath == null){
                Memberinfo memberinfo = new Memberinfo(name);
                uploader(memberinfo);
            }else{
                try {

                    InputStream stream = new FileInputStream(new File(profilePath));

                    UploadTask uploadTask = mountainImagesRef.putStream(stream);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return mountainImagesRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                Memberinfo memberinfo = new Memberinfo(name,downloadUri.toString());
                                uploader(memberinfo);
                            } else {
                                startToast("회원정보를 보내는데 실패하였습니다");
                                Log.e("로그","실패");
                            }
                        }
                    });

                }catch (FileNotFoundException e){
                    Log.e("로그: ","에러: "+e.toString());
                }
            }

        }else {
            startToast("사용자 이름을 입력해 주세요.");
        }
    }

    private void  startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent,0);
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivityForResult(intent,0);
    }

    private void uploader(Memberinfo memberinfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).set(memberinfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startToast("프로필 설정을 성공하였습니다.");
                        startMainActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("프로필 설정에 실패하였습니다.");
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}






















