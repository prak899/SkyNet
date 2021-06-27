package com.siddhi.skynet.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.siddhi.skynet.Activity.ServiceBook;
import com.siddhi.skynet.Adapter.MasterAdapter;
import com.siddhi.skynet.AdminAdapter.OrderListAdapter;
import com.siddhi.skynet.Model.MasterModel;
import com.siddhi.skynet.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceOrder extends AppCompatActivity {

    private FirebaseFirestore db;
    ArrayList<OrderListModel> orderListModels = new ArrayList<>();
    RecyclerView recyclerView;
    private OrderListAdapter orderListAdapter;

    TextView EmptyView, HeaderName;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_order);
        init();


        db = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        seoM();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.dotBounce);
        EmptyView = (TextView) findViewById(R.id.empty_view);

        HeaderName= findViewById(R.id.header_name);
        //checkBox= findViewById(R.id.checkservice);
    }
    public void seoM(){

        db.collection("Orders").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            progressBar.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                OrderListModel c = d.toObject(OrderListModel.class);
                                orderListModels.add(c);
                            }
                            orderListAdapter = new OrderListAdapter(orderListModels, ServiceOrder.this);
                            recyclerView.setAdapter(orderListAdapter);
                            orderListAdapter.notifyDataSetChanged();

                            if (orderListModels.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                EmptyView.setVisibility(View.VISIBLE);
                            }
                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                EmptyView.setVisibility(View.GONE);
                            }
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ServiceOrder.this, "No order yet!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            EmptyView.setText("No order yet!");
                            EmptyView.setVisibility(View.VISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // if we do not get any data or any error we are displaying
                            // a toast message that we do not get any data
                            Toast.makeText(ServiceOrder.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            EmptyView.setText("Fail to get the data.");
                            EmptyView.setVisibility(View.VISIBLE);
                        }
                    });
    }

}