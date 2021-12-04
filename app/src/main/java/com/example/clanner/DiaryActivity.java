package com.example.clanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {
    private static final String TAG = "DiaryActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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
        recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.btnDiarywrite).setOnClickListener(onClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DiaryActivity.this));
    }

    //activity가 재실행되거나, 포커스 맞춰졌을때 새로고침 해주는 부분
    @Override
    protected void onResume(){
        super.onResume();

        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("cities");
            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<WriteInfo> postList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    postList.add(new WriteInfo(
                                            document.getData().get("title").toString(),
                                            (ArrayList<String>) document.getData().get("content"),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId()));
                                }
                                RecyclerView.Adapter mAdapter = new DiaryAdapter(DiaryActivity.this,postList);
                                recyclerView.setAdapter(mAdapter);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }



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


    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
}
