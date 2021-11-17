package com.example.clanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class scheduleRCViewAdapter extends RecyclerView.Adapter<scheduleRCViewAdapter.scheduleViewholder> {

    ArrayList<scheduleClass> list;
    Context context;

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

