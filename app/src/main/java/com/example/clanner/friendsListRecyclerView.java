package com.example.clanner;

import android.content.Context;
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

import java.util.ArrayList;

public class friendsListRecyclerView extends RecyclerView.Adapter<friendsListRecyclerView.friendsViewHolder>{


    Context context;
    ArrayList<Memberinfo> list = new ArrayList<Memberinfo>();
    static String fragmentKind;

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class friendsViewHolder extends RecyclerView.ViewHolder {
        ImageView friendsImage;
        TextView friendsName;
        TextView friendsEmail;

        public friendsViewHolder(@NonNull View itemView) {
            super(itemView);

            friendsImage = itemView.findViewById(R.id.friendsProfilePicture_friendsManageRecyclerview);
            friendsName = itemView.findViewById(R.id.friendsID_friendsManageRecyclerview);
            friendsEmail = itemView.findViewById(R.id.friendsEmail_friendsManageRecyclerview);

            Button allowBtn = itemView.findViewById(R.id.allowButton);
            Button rejectBtn = itemView.findViewById(R.id.rejectButton);

            if(fragmentKind.equals("StayFriendList")){
                allowBtn.setVisibility(View.VISIBLE);
                rejectBtn.setVisibility(View.VISIBLE);
            }else if(fragmentKind.equals("friendList")){
                allowBtn.setVisibility(View.GONE);
                rejectBtn.setVisibility(View.GONE);
            }

            allowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "수락하셨습니다",Toast.LENGTH_SHORT).show();
                }
            });
            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Toast.makeText(view.getContext(),"거절하셨습니다",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
