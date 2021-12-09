package com.example.clanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class friendsListFragment extends Fragment {

    ArrayList<Memberinfo> list = new ArrayList<>();
    friendsListRecyclerView adapter;

    //DB연결
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference friencdsList;

    //현재 유저 정보 획득
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    public friendsListFragment() {
        // Required empty public constructor
    }

    public static friendsListFragment newInstance() {
        friendsListFragment fragment = new friendsListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayoutManager LNmanager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false );
        adapter = new friendsListRecyclerView(getActivity(),list,"friendList");

        View friendView = inflater.inflate(R.layout.fragment_friends_list, container, false);
        RecyclerView RCView = friendView.findViewById(R.id.friendListRCView_friendListFragment);
        RCView.setLayoutManager(LNmanager);
        RCView.setAdapter(adapter);

        getData();

        return friendView;
    }
    private void getData(){
        DatabaseReference friendsReference = reference.child("user").child(user.getUid()).child("friends");
        friendsReference.addChildEventListener(new ChildEventListener() {
            String email;
            String photoURL;
            String nickname;
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                email = snapshot.child("email").getValue().toString();
                photoURL = snapshot.child("user_photo").getValue().toString();
                nickname = snapshot.child("user_nickname").getValue().toString();

                if(snapshot.child("status").getValue().toString().equals("allow")){
                    list.add(new Memberinfo(nickname,photoURL,email));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                email = snapshot.child("email").getValue().toString();
                photoURL = snapshot.child("user_photo").getValue().toString();
                nickname = snapshot.child("user_nickname").getValue().toString();

                if(snapshot.child("status").getValue().toString().equals("allow")){
                    list.add(new Memberinfo(nickname,photoURL,email));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}