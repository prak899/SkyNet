package com.siddhi.skynet.AdminAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siddhi.skynet.Activity.CostomActivity;
import com.siddhi.skynet.AdminModel.AdminDataModel;
import com.siddhi.skynet.Model.MasterModel;
import com.siddhi.skynet.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdminDataAdapter extends RecyclerView.Adapter<AdminDataAdapter.ViewHolder>{
    private List<AdminDataModel> shops;
    private Context mContext;

    public AdminDataAdapter(List<AdminDataModel> lists, Context mContext) {
        this.shops = lists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_list,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdminDataModel sp=shops.get(position);

        holder.AdminUsername.setText("Username:-"+sp.getUserName());
        holder.AdminPassword.setText("Password:-"+sp.getPassword());
        holder.AdminCreatedAt.setText("Date:-"+sp.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView AdminUsername, AdminPassword, AdminCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);

            AdminUsername=(TextView)itemView.findViewById(R.id.username);
            AdminPassword=(TextView)itemView.findViewById(R.id.password);
            AdminCreatedAt=(TextView)itemView.findViewById(R.id.createdAt);
        }
    }
}
