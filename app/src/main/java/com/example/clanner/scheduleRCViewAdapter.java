package com.example.clanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class scheduleRCViewAdapter extends RecyclerView.Adapter<scheduleRCViewAdapter.scheduleViewholder> {

    ArrayList<scheduleClass> list;
    Context context;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user =auth.getCurrentUser();
    DatabaseReference scheduleReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).child("schedule");

    //생성자
    public scheduleRCViewAdapter(ArrayList<scheduleClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //커스텀 뷰홀더 구현
    public class scheduleViewholder extends RecyclerView.ViewHolder {

        CheckBox CbTodaySchedule;
        ImageView alarmImage;

        public scheduleViewholder(@NonNull View itemView) {
            super(itemView);

            CbTodaySchedule = (CheckBox) itemView.findViewById(R.id.checkBox_dayScheduleRecyclerview);
            alarmImage = (ImageView) itemView.findViewById(R.id.bellOnoffImage_dayScheduleRecyclerview);
            CbTodaySchedule.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("CLANNER")
                            .setMessage("삭제하시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //데이터 삭제하는 로직 들어가야됨.
                                    Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //아무 일도 안함
                                    Toast.makeText(context,"취소하셧습니다",Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.show();
                    return true;
                }
            });
        }
    }


    @NonNull

    //아이템을 뿌려줄 뷰
    @Override
    public scheduleRCViewAdapter.scheduleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_schedule_recyclerview,parent,false);
        scheduleViewholder holder = new scheduleViewholder(view);
        return holder;
    }

    //아이템 내용 바인딩
    @Override
    public void onBindViewHolder(@NonNull scheduleRCViewAdapter.scheduleViewholder holder, int position) {
        //알람 켜고 끈 상황에 따라 벨 이미지 변경
        if (list.get(position).getAlarmState() == 1) {
            holder.alarmImage.setImageResource(R.drawable.bell_on);
        } else {
            holder.alarmImage.setImageResource(R.drawable.bell_off);
        }


        //체크박스의 텍스트 부분에 할 일 적어두기
        holder.CbTodaySchedule.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

