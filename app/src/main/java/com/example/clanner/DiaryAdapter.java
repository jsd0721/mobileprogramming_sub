package com.example.clanner;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private ArrayList<WriteInfo> mDataset;
    private Activity activity;
    private FirebaseFirestore firebaseFirestore;

    public static class DiaryViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public DiaryViewHolder(Activity activity, CardView v, WriteInfo writeInfo){
            super(v);
            cardView = v;

            LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> contentList = writeInfo.getContent();
            if (contentsLayout.getChildCount() == 0) {
                for (int i=0; i < contentList.size(); i++){  //content에 있는만큼 반복
                    String content = contentList.get(i);
                    if (Patterns.WEB_URL.matcher(content).matches()){
                        ImageView imageView = new ImageView(activity);
                        imageView.setLayoutParams(layoutParams);

                        //다이어리에 나오는 이미지를 딱맞는 크기로 출력해주는 부분
                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                        contentsLayout.addView(imageView);
                    }else {
                        TextView textView = new TextView(activity);
                        textView.setLayoutParams(layoutParams);
                        contentsLayout.addView(textView);
                    }
                }
            }
        }
    }

    public DiaryAdapter(Activity activity, ArrayList<WriteInfo> myDataset){
        mDataset = myDataset;
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @NonNull
    @Override
    public DiaryAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary,parent,false);
        final DiaryViewHolder diaryViewHolder = new DiaryViewHolder(activity, cardView, mDataset.get(viewType)); //positiontype = viewType 으로 알 수  있다.
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메뉴기능 추가
                showPopup(v,diaryViewHolder.getAdapterPosition());
            }
        });
        return diaryViewHolder;
    }

    /* 링크가 순서대로 들어오면서 이미지가 나옴 */
    @Override
    public void onBindViewHolder(final DiaryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
        titleTextView.setText(mDataset.get(position).getTitle());

        TextView createAtTextView = cardView.findViewById(R.id.createAtTextView);
        createAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));

        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
        ArrayList<String> contentList = mDataset.get(position).getContent();

        for (int i=0; i < contentList.size(); i++){  //content에 있는만큼 반복
            String content = contentList.get(i);
            if (Patterns.WEB_URL.matcher(content).matches()){
                Glide.with(activity).load(content).override(1000).thumbnail(0.1f).into((ImageView)contentsLayout.getChildAt(i)); //thumbnail(_) => 흐릿하게 이미지 보여줌
            }else {
                ((TextView)contentsLayout.getChildAt(i)).setText(content);
            }
        }
    }
    //titleTextView.setText((mDataset.get(position).getTitle()));
    //mDataset.get(position) => URI

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void showPopup(View v,int position) {
        PopupMenu popup = new PopupMenu(activity, v);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.modify:

                        return true;
                    case R.id.delete:
                        firebaseFirestore.collection("cities").document(mDataset.get(position).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(activity,"삭제하였습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(activity,"삭제를 실패했습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                });

                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.writediary, popup.getMenu());
        popup.show();
    }
}
