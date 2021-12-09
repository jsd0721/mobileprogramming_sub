package com.example.clanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link stayFriendsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stayFriendsListFragment extends Fragment {

    ArrayList<Memberinfo> list = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DBReference = database.getReference();
    friendsListRecyclerView adapter;

    public stayFriendsListFragment() {
        // Required empty public constructor
    }


    public static stayFriendsListFragment newInstance() {
        stayFriendsListFragment fragment = new stayFriendsListFragment();
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
        adapter = new friendsListRecyclerView(getActivity(),list,"StayFriendList");

        View view = inflater.inflate(R.layout.fragment_stay_friends_list, container, false);

        RecyclerView RCView = view.findViewById(R.id.stayfriendListRCView_stayFriendListFragment);
        Button allowBtn = view.findViewById(R.id.allowButton);
        Button rejectBtn = view.findViewById(R.id.rejectButton);
        RCView.setLayoutManager(LNmanager);
        RCView.setAdapter(adapter);

        getData();


        // Inflate the layout for this fragment
        return view;
    }

    private void getData(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        DBReference.child("user").child(user.getUid()).child("friends").addChildEventListener(new ChildEventListener() {
            String nickname;
            String email;
            String photoURL;
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                nickname = snapshot.child("user_nickname").getValue().toString();
                email = snapshot.child("email").getValue().toString();
                photoURL = snapshot.child("user_photo").getValue().toString();

                if(snapshot.child("status").getValue().toString().equals("stay")){
                    list.add(new Memberinfo(nickname,photoURL,email));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                nickname = snapshot.child("user_nickname").getValue().toString();
                email = snapshot.child("email").getValue().toString();
                photoURL = snapshot.child("user_photo").getValue().toString();

                if(snapshot.child("status").getValue().toString().equals("stay")) {
                    list.add(new Memberinfo(nickname, photoURL, email));
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

//        DBReference.child("user").child(user.getUid()).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot data : snapshot.getChildren()){
//                    String data1 = data.child("status").getValue().toString();
//                    if(data1.equals("stay")){
//                        String email = data.child("email").getValue().toString();
//                        String nickname = data.child("user_nickname").getValue().toString();
//                        String photoURL = data.child("user_photo").getValue().toString();
//                        try {
//                            Log.d("데이터들 ", "이메일 = " + email + "닉네임 = " + nickname + "사진주소 = " + photoURL );
//                            list.add(new Memberinfo(nickname,photoURL,email));
//                            adapter.notifyDataSetChanged();
//                        }catch(Exception e){
//                            Log.e("error",e.toString());
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}