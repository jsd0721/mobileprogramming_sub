package com.example.clanner;
/*
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryActivity.GalleryViewHolder> {
    private String[] mDataset;

    public static class GalleryViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public GalleryViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }
    public GalleryAdapter(String[] myDataset){
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public GalleryActivity.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view,parent,false);
        GalleryViewHolder vh = new GalleryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryActivity.GalleryViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.textView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        mDataset.length;
    }
}
*/