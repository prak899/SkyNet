package com.siddhi.skynet.AdminAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siddhi.skynet.Admin.OrderListModel;
import com.siddhi.skynet.Class.Anim;
import com.siddhi.skynet.R;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<OrderListModel> coursesArrayList;
    private Context context;

    public OrderListAdapter(ArrayList<OrderListModel> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder holder, int position) {
        OrderListModel orderListModel = coursesArrayList.get(position);
        holder.serviceName.setText(orderListModel.getServiceName());
        holder.paymentStatus.setText(orderListModel.getPaymentStatus());
        holder.serviceType.setText(orderListModel.getServiceType());
        holder.servicePrice.setText(orderListModel.getServicePrice());

        holder.contactNumber.setText(orderListModel.getUserNumber());
        holder.contactMail.setText(orderListModel.getUserEmail());
        holder.contactLocation.setText(orderListModel.getUserAddress());

        holder.orderQn.setText(orderListModel.getOrderQuantity());

        holder.userDownContact.setOnClickListener(v->{

            Anim cls2 = new Anim();
            cls2.slideUp(holder.contactLayout);
            holder.userDownContact.setVisibility(View.GONE);
            holder.userUpContact.setVisibility(View.VISIBLE);
        });
        holder.userUpContact.setOnClickListener(v->{

            Anim cls2 = new Anim();
            cls2.slideDown(holder.contactLayout);
            holder.userUpContact.setVisibility(View.GONE);
            holder.userDownContact.setVisibility(View.VISIBLE);
        });

        holder.Accepted.setOnClickListener(v->{
            Toast.makeText(context, "Order Acepted", Toast.LENGTH_SHORT).show();
        });
        holder.Rejected.setOnClickListener(v->{
            Toast.makeText(context, "Order Rejected", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return coursesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView serviceName, contactNumber, userUpContact, servicePrice;
        private final TextView paymentStatus, contactMail;
        private final TextView serviceType, contactLocation, userDownContact;
        private final TextView Accepted, Rejected;

        private final TextView orderQn;
        private final LinearLayout contactLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = (TextView) itemView.findViewById(R.id.servicename);
            paymentStatus = (TextView) itemView.findViewById(R.id.paymentStatus);
            serviceType = (TextView) itemView.findViewById(R.id.serviceType);
            servicePrice = (TextView) itemView.findViewById(R.id.servicePrice);

            contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);
            contactMail = (TextView) itemView.findViewById(R.id.contactMail);
            contactLocation = (TextView) itemView.findViewById(R.id.contactLocation);

            userDownContact = (TextView) itemView.findViewById(R.id.userDownContact);
            userUpContact = (TextView) itemView.findViewById(R.id.userUpContact);

            Accepted = (TextView) itemView.findViewById(R.id.accept);
            Rejected = (TextView) itemView.findViewById(R.id.reject);

            orderQn = (TextView) itemView.findViewById(R.id.orderQuantity);
            contactLayout = (LinearLayout) itemView.findViewById(R.id.contactLayout);
        }
    }
}