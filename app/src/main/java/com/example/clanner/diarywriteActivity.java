package com.example.clanner;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

// 다이어리 작성 부분에서 버튼 클릭했을때 발생하는 부분

public class diarywriteActivity extends AppCompatActivity {
    private static final String TAG = "diarywriteActivity";
    private FirebaseUser user;
    private ArrayList<String> pathList = new ArrayList<>();
    private LinearLayout parent;
    private int pathCount,successCount;
    private RelativeLayout buttonsBackgroundLayout;
    private ImageView selectedImageView;
    private EditText selectedEditText;
    private EditText content;
    private EditText title;
    private WriteInfo writeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarywrite);

        parent = findViewById(R.id.contentsLayout);
        buttonsBackgroundLayout = findViewById(R.id.buttonsBackgroundLayout);
        content = findViewById(R.id.content);
        title = findViewById(R.id.title);

        buttonsBackgroundLayout.setOnClickListener(onClickListener);

        findViewById(R.id.btnsave).setOnClickListener(onClickListener);
        findViewById(R.id.image).setOnClickListener(onClickListener);
        findViewById(R.id.imageModify).setOnClickListener(onClickListener);
        findViewById(R.id.delete).setOnClickListener(onClickListener);
        content.setOnFocusChangeListener(onFocusChangeListener);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectedEditText = null;
                }
            }
        });
        // 이부분 다시한번 확인!

        writeInfo = (WriteInfo)getIntent().getSerializableExtra("writeInfo");
        writeInit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    String profilePath = data.getStringExtra("profilePath");
                    pathList.add(profilePath);

                    //아래 경로대로 imageview 생성해서 넣어주는 부분
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    LinearLayout linearLayout = new LinearLayout(diarywriteActivity.this);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    parent.addView(linearLayout);

                    ImageView imageView = new ImageView(diarywriteActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true); //이미지(1)
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY); //딱맞게(2)
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonsBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v; //선택될때마다 버튼들이 나오면서 현재 imageview가 뭔지 알 수 있는 부분
                        }
                    });
                    Glide.with(this).load(profilePath).override(1000).into(imageView);
                    linearLayout.addView(imageView);

                    /*EditText editText = new EditText(diarywriteActivity.this);
                    editText.setLayoutParams(layoutParams);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);
                    parent.addView(editText);*/
                }
                break;
                case 1:
                    if (resultCode == Activity.RESULT_OK){
                        String profilePath = data.getStringExtra("profilePath");
                        Glide.with(this).load(profilePath).override(1000).into(selectedImageView);
                    }
                    break;

            }
        }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                selectedEditText = (EditText) v;
            }
        }
    };


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnsave:
                    storageUploader();
                    startDiaryActicity();
                    break;
                case R.id.image:
                    myStartActivity(GalleryActivity.class,0);
                    break;
                case R.id.buttonsBackgroundLayout:
                    if (buttonsBackgroundLayout.getVisibility() == View.VISIBLE){
                        buttonsBackgroundLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.imageModify:
                    myStartActivity(GalleryActivity.class,1);
                    buttonsBackgroundLayout.setVisibility(View.GONE);
                    break;
                case R.id.delete:
                    parent.removeView((View)selectedImageView.getParent());
                    buttonsBackgroundLayout.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void storageUploader() {
        String title = ((EditText) findViewById(R.id.title)).getText().toString();
        String content = ((EditText) findViewById(R.id.content)).getText().toString();

        if (title.length() > 0 && content.length() > 0) {
            ArrayList<String> contentList = new ArrayList<>();
            user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = writeInfo == null ? firebaseFirestore.collection("cities").document() : firebaseFirestore.collection("cities").document(writeInfo.getId());//id값을 얻기 위해 선언
            final Date date = writeInfo == null ? new Date() : writeInfo.getCreatedAt();
            for (int i=0; i<parent.getChildCount(); i++){
                View view = parent.getChildAt(i);
                if (view instanceof EditText){
                    String text = ((EditText) view).getText().toString();
                    if (text.length() > 0){
                        contentList.add(text);
                    }
                }else{
                    contentList.add(pathList.get(pathCount));

                    /* 0부터 순서대로 정리되는 부분 */
                    final StorageReference mountainImagesRef = storageRef.child("posts/"+documentReference.getId()+"/"+pathCount+".jpg");
                    try {
                        InputStream stream = new FileInputStream(new File(pathList.get(pathCount)));

                        //정확한위치를 찾기위해 메타데이터생성
                        StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("index",""+(contentList.size()-1)).build();

                        UploadTask uploadTask = mountainImagesRef.putStream(stream,metadata);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                final int index = Integer.parseInt(taskSnapshot.getMetadata().getCustomMetadata("index"));

                                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        contentList.set(index, uri.toString());
                                        successCount++;
                                        if (pathList.size() == successCount){
                                            //완료
                                            WriteInfo writeInfo = new WriteInfo(title,contentList,user.getUid(), date);
                                            storeUploader(documentReference, writeInfo);
                                            /*for(int a=0; a<contentList.size(); a++){
                                            Log.e("로그:","콘텐츠:"+contentList.get(a)); }
                                             */
                                        }
                                    }
                                });
                            }
                        });
                    }catch (FileNotFoundException e){
                        Log.e("로그: ","에러: "+e.toString());
                    }
                    pathCount++;
                }
            }
            if (pathList.size() == 0){
                storeUploader(documentReference, new WriteInfo(title,contentList,user.getUid(), date));
            }
        }else{
            startToast("입력하세요.");
        }
    }


    private void storeUploader(DocumentReference documentReference, WriteInfo writeInfo) {
        documentReference.set(writeInfo)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                    finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error writing document", e);
                }
            });
/*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(writeInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
*/
    }


        private void startToast (String msg){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

    private void myStartActivity(Class c, int request){
        Intent intent = new Intent(this,c);
        startActivityForResult(intent,request);
    }

    private void startDiaryActicity() {
        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }

    //다이어리 작성 - text 수정 부분
    private void writeInit(){
        if (writeInfo != null){
            title.setText(writeInfo.getTitle());
            ArrayList<String> contentList = writeInfo.getContent();
            for (int i=0; i<contentList.size(); i++){
                String contents =contentList.get(i);
                if (Patterns.WEB_URL.matcher(contents).matches() && contents.contains("https://firebasestorage.googleapis.com/v0/b/clanner-2087a.appspot.com/o/posts")){
                    pathList.add(contents);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    LinearLayout linearLayout = new LinearLayout(diarywriteActivity.this);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    parent.addView(linearLayout);

                    ImageView imageView = new ImageView(diarywriteActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true); //이미지(1)
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY); //딱맞게(2)
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonsBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v; //선택될때마다 버튼들이 나오면서 현재 imageview가 뭔지 알 수 있는 부분
                        }
                    });
                    Glide.with(this).load(content).override(1000).into(imageView);
                    linearLayout.addView(imageView);

                    EditText editText = new EditText(diarywriteActivity.this);
                    editText.setLayoutParams(layoutParams);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);

                    if(i < contentList.size() - 1){
                        String nextContent = contentList.get(i + 1);
                        if(!Patterns.WEB_URL.matcher(nextContent).matches() || !nextContent.contains("https://firebasestorage.googleapis.com/v0/b/clanner-2087a.appspot.com/o/posts"))
                        {
                            editText.setText(nextContent);
                        }
                    }
                    editText.setOnFocusChangeListener(onFocusChangeListener);
                    linearLayout.addView(editText);
                }else if (i == 0){
                    content.setText(contents);
                }
            }
        }
    }

}