package com.example.clanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {
    private static final String TAG = "DiaryActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageRef;
    private RecyclerView recyclerView;
    private DiaryAdapter diaryAdapter;
    private ArrayList<WriteInfo> postList;
    private int successCount;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        if (firebaseUser == null) {
            myStartActivity(JoinActivity.class);
        }else {
            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener((task) -> {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document != null){
                        if (document.exists()){
                            Log.d(TAG,"DocumentSnapshot data: "+document.getData());
                        }else{
                            Log.d(TAG,"No such document");
                            myStartActivity(profileActivity.class);
                        }
                    }
                }else{
                    Log.d(TAG,"get failed with", task.getException());
                }
            });
/*
            documentReference.collection("cities")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<WriteInfo> postList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    postList.add(new WriteInfo(document.getData().get("title").toString(),
                                            (ArrayList<String>) document.getData().get("content"),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("createdAt").getTime())));
                                }
                                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(DiaryActivity.this));

                                RecyclerView.Adapter mAdapter = new DiaryAdapter(DiaryActivity.this,postList);
                                recyclerView.setAdapter(mAdapter);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

 */

        }
        postList = new ArrayList<>();
        diaryAdapter = new DiaryAdapter(DiaryActivity.this,postList);
        diaryAdapter.setOnWriteListener(onWriteListener);

        recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.btnDiarywrite).setOnClickListener(onClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DiaryActivity.this));
        recyclerView.setAdapter(diaryAdapter);
    }

    //activity가 재실행되거나, 포커스 맞춰졌을때 새로고침 해주는 부분
    @Override
    protected void onResume(){
        super.onResume();
        diaryUpdate();
    }

    OnWriteListener onWriteListener = new OnWriteListener() {
        @Override
        public void onDelete(int position) {
            final String id = postList.get(position).getId();

            ArrayList<String> contentList = postList.get(position).getContent();
            for (int i=0; i<contentList.size(); i++){
                String content =contentList.get(i);
                if (Patterns.WEB_URL.matcher(content).matches() && content.contains("https://firebasestorage.googleapis.com/v0/b/clanner-2087a.appspot.com/o/posts")){
                    // 삭제 로직 ( url 찾아내서 삭제 )
                    successCount++;
                    String[] list = content.split("\\?");
                    String[] list2= list[0].split("%2F");
                    String name = list2[list2.length - 1];
                    StorageReference desertRef = storageRef.child("posts/"+id+"/"+name);
                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            successCount--;
                            storeUploader(id);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            startToast("삭제를 실패했습니다.");
                            // Uh-oh, an error occurred!
                        }
                    });
                }
            }
            storeUploader(id);
        }

        @Override
        public void onModify(int position) {
            String id = postList.get(position).getId();
            myStartActivity(diarywriteActivity.class, id);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnDiarywrite:
                    myStartActivity(diarywriteActivity.class);
                    break;
            }
        }
    };

    private void diaryUpdate(){
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("cities");
            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                postList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    postList.add(new WriteInfo(
                                            document.getData().get("title").toString(),
                                            (ArrayList<String>) document.getData().get("content"),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId()));
                                }
                                diaryAdapter.notifyDataSetChanged(); //바뀐 postlist로 change
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }


    private void startToast (String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

    private void myStartActivity(Class c, String id){
        Intent intent = new Intent(this,c);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    private void storeUploader(String id){
        if (successCount == 0){
            firebaseFirestore.collection("cities").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startToast("삭제하였습니다.");
                            diaryUpdate();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startToast("삭제를 실패했습니다.");
                        }
                    });
            }
        }
}
