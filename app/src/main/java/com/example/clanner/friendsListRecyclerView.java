package com.example.clanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class friendsListRecyclerView extends RecyclerView.Adapter<friendsListRecyclerView.friendsViewHolder>{


    Context context;
    ArrayList<Memberinfo> list = new ArrayList<Memberinfo>();
    static String fragmentKind;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public friendsListRecyclerView(Context context, ArrayList<Memberinfo> list,String fragmentKind){
        this.context = context;
        this.list = list;
        this.fragmentKind = fragmentKind;
    }

    @NonNull
    @Override
    public friendsListRecyclerView.friendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_manage_recyclerview,parent,false  );
        friendsViewHolder holder = new friendsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull friendsListRecyclerView.friendsViewHolder holder, int position) {
        holder.friendsEmail.setText(list.get(position).getEmail());
        Glide.with(context).load(list.get(position).getPhotoUrl()).into(holder.friendsImage);
        holder.friendsName.setText(list.get(position).getName());

        holder.allowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeData(list.get(position).getEmail());
                Toast.makeText(view.getContext(), "수락하셨습니다",Toast.LENGTH_SHORT).show();
            }
        });
        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"거절하셨습니다",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class friendsViewHolder extends RecyclerView.ViewHolder {
        ImageView friendsImage;
        TextView friendsName;
        TextView friendsEmail;
        Button allowBtn;
        Button rejectBtn;

        public friendsViewHolder(@NonNull View itemView) {
            super(itemView);

            friendsImage = itemView.findViewById(R.id.friendsProfilePicture_friendsManageRecyclerview);
            friendsName = itemView.findViewById(R.id.friendsID_friendsManageRecyclerview);
            friendsEmail = itemView.findViewById(R.id.friendsEmail_friendsManageRecyclerview);

            allowBtn = itemView.findViewById(R.id.allowButton);
            rejectBtn = itemView.findViewById(R.id.rejectButton);

            if(fragmentKind.equals("StayFriendList")){
                allowBtn.setVisibility(View.VISIBLE);
                rejectBtn.setVisibility(View.VISIBLE);
            }else if(fragmentKind.equals("friendList")){
                allowBtn.setVisibility(View.GONE);
                rejectBtn.setVisibility(View.GONE);
            }

        }
    }

    //수락하는 로직
    private void changeData(String email){
        DatabaseReference friendsReference = reference.child("user").child(user.getUid()).child("friends");
        friendsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String key = data.getKey();
                    Log.d("키값",key);
                    String emailData = data.child("email").getValue().toString();
                    if(emailData.equals(email)){
                        friendsReference.child(key).child("status").setValue("allow");
//                        Log.d("수락하신 프로필은",data.child("user_nickname").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
