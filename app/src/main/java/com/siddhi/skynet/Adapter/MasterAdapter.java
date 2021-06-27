package com.siddhi.skynet.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siddhi.skynet.Activity.CostomActivity;
import com.siddhi.skynet.Model.MasterModel;
import com.siddhi.skynet.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.ViewHolder>{
    private List<MasterModel> shops;
    private Context mContext;

    public MasterAdapter(List<MasterModel> lists, Context mContext) {
        this.shops = lists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MasterModel sp=shops.get(position);
        holder.ServiceName.setText(sp.getServiceName());
        holder.servicePrice.setText("Rs "+sp.getServicePrice()+".0");

        if (sp.getImage()!= null) {
            Glide.with(mContext).load(sp.getImage()).into(holder.serviceImage);

        }else {
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(holder.serviceImage);

        }

        holder.checkService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final boolean isChecked = holder.checkService.isChecked();

                    if (isChecked) {
                        Intent intent = new Intent(mContext, CostomActivity.class);
                        intent.putExtra("a", sp.getServiceName());
                        intent.putExtra("b", sp.getServicePrice());
                        intent.putExtra("c", sp.getServiceType());
                        intent.putExtra("d", sp.getImage());
                        mContext.startActivity(intent);
                    }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, CostomActivity.class);
                intent.putExtra("a", sp.getServiceName());
                intent.putExtra("b", sp.getServicePrice());
                intent.putExtra("c", sp.getServiceType());
                intent.putExtra("d", sp.getImage());
                mContext.startActivity(intent);
            }
        });

        holder.serviceCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, CostomActivity.class);
                intent.putExtra("a", sp.getServiceName());
                intent.putExtra("b", sp.getServicePrice());
                intent.putExtra("c", sp.getServiceType());
                intent.putExtra("d", sp.getImage());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView ServiceName,  servicePrice;
        private CheckBox checkService;
        private ImageView serviceCart;
        private CircleImageView serviceImage;
        ViewGroup viewGroup;
        public ViewHolder(View itemView) {
            super(itemView);

            ServiceName=(TextView)itemView.findViewById(R.id.tvContent);
            servicePrice=(TextView)itemView.findViewById(R.id.tvDescription);

            checkService=(CheckBox)itemView.findViewById(R.id.chbContent);
            viewGroup = itemView.findViewById(android.R.id.content);

            serviceCart=(ImageView)itemView.findViewById(R.id.serviceCart);
            serviceImage=(CircleImageView)itemView.findViewById(R.id.gallery_img_view);

        }
    }
}
