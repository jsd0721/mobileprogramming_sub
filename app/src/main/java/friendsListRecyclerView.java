import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clanner.Memberinfo;
import com.example.clanner.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class friendsListRecyclerView extends RecyclerView.Adapter<friendsListRecyclerView.friendsViewHolder>{


    Context context;
    ArrayList<Memberinfo> list = new ArrayList<Memberinfo>();

    public friendsListRecyclerView(Context context, ArrayList<Memberinfo> list){
        this.context = context;
        this.list = list;
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
//        holder.friendsImage.setImageResource(list.get(position).getPhotoUrl());
        holder.friendsName.setText(list.get(position).getEmail());
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

        }
    }
}
