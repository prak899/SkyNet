package com.siddhi.skynet.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.siddhi.skynet.AdminAdapter.Adapter;
import com.siddhi.skynet.AdminModel.Survey;
import com.siddhi.skynet.R;

import java.util.ArrayList;

public class ServiceEntry extends AppCompatActivity {

    private FirebaseFirestore db;
    TextView TotalOrder, TotalAdmin, TotalService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_entry);
        init();
        totalOrder();
        totalService();

        ArrayList<Survey> cities = initCities();


        RecyclerView recyclerView = findViewById(R.id.formRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter adapter = new Adapter(cities);
        recyclerView.setAdapter(adapter);


    }

    private ArrayList<Survey> initCities() {
        ArrayList<Survey> list = new ArrayList<>();

        list.add(new Survey("Service Entry", "For adding new service for user's"));
        list.add(new Survey("Order Deatils", "For viewing order deatils"));
        list.add(new Survey("Admin Deatils", "For viewing admin deatils"));
        list.add(new Survey("Milestones", "For viewing your application growth"));

        return list;
    }
    public void init(){
        TotalOrder= (TextView) findViewById(R.id.totalOrder);
        TotalAdmin= (TextView) findViewById(R.id.totalAdmin);
        TotalService= (TextView) findViewById(R.id.totalService);

        db = FirebaseFirestore.getInstance();
    }
    private void totalOrder(){
        db.collection("Orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("XchdfgX", task.getResult().size() + "");
                    TotalOrder.setText(Integer.toString(task.getResult().size()));
                } else {
                    Log.d("XconX", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    private void totalService(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Log.e(snap.getKey(),snap.getChildrenCount() + "");
                    long s= snap.getChildrenCount();

                    if (snap.getKey().equals("ServiceUsers"))
                        TotalService.setText(String.valueOf(s));
                    else if (snap.getKey().equals("serviceAD"))
                        TotalAdmin.setText(String.valueOf(s));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}