package com.siddhi.skynet.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.siddhi.skynet.Activity.CostomActivity;
import com.siddhi.skynet.Model.MasterModel;
import com.siddhi.skynet.R;

import java.util.List;


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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MasterModel sp=shops.get(position);
        holder.ServiceName.setText(sp.getServiceName());
        holder.servicePrice.setText(sp.getServicePrice());
        //holder.checkService.setText(sp.getCheckService());


        holder.checkService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final boolean isChecked = holder.checkService.isChecked();

                    if (isChecked) {
                        Intent intent= new Intent(mContext, CostomActivity.class);
                        intent.putExtra("a", sp.getServiceName());
                        intent.putExtra("b", sp.getServicePrice());
                        intent.putExtra("c", sp.getCheckService());
                        mContext.startActivity(intent);

                    } else {
                        Toast.makeText(mContext, sp.getServicePrice(), Toast.LENGTH_SHORT).show();
                    }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.activity_costom, holder.viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
        ViewGroup viewGroup;
        public ViewHolder(View itemView) {
            super(itemView);

            ServiceName=(TextView)itemView.findViewById(R.id.tvContent);
            servicePrice=(TextView)itemView.findViewById(R.id.tvDescription);

            checkService=(CheckBox)itemView.findViewById(R.id.chbContent);
            viewGroup = itemView.findViewById(android.R.id.content);
        }
    }
}
