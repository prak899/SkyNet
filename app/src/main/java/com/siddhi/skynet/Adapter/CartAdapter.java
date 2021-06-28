package com.siddhi.skynet.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.siddhi.skynet.Activity.CostomActivity;
import com.siddhi.skynet.Model.CartModel;
import com.siddhi.skynet.Model.MasterModel;
import com.siddhi.skynet.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private List<CartModel> shops;
    private Context mContext;

    public CartAdapter(List<CartModel> lists, Context mContext) {
        this.shops = lists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel sp=shops.get(position);
        holder.cartName.setText(sp.getServiceName());
        holder.cartPrice.setText("Rs "+sp.getServicePrice()+".0");

        if (sp.getServiceImage()!= null) {
            Glide.with(mContext).load(sp.getServiceImage()).into(holder.cartImageCircular);

        }else {
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(holder.cartImageCircular);

        }

        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                String userNumber= prefs.getString("UserNumber", "0");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ServiceCart");
                ref.child(userNumber).orderByChild(userNumber)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                for (DataSnapshot ds : dataSnapshot.getChildren())
                                {
                                    ds.getRef().removeValue();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                Toast.makeText(mContext, "Failed to delete", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView cartName,  cartPrice;

        private CircleImageView cartImageCircular;
        private ImageView cartDelete;
        public ViewHolder(View itemView) {
            super(itemView);

            cartName=(TextView)itemView.findViewById(R.id.serviceName);
            cartPrice=(TextView)itemView.findViewById(R.id.servicePrice);


            cartImageCircular=(CircleImageView)itemView.findViewById(R.id.cartImage);
            cartDelete=(ImageView)itemView.findViewById(R.id.cartDelete);

        }
    }
}
