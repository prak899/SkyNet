package com.siddhi.skynet.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.siddhi.skynet.AdminAdapter.Adapter;
import com.siddhi.skynet.AdminModel.Survey;
import com.siddhi.skynet.R;

import java.util.ArrayList;

public class ServiceEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_entry);

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
        list.add(new Survey("Users Deatils", "For viewing user deatils"));
        list.add(new Survey("Milestones", "For viewing your application growth"));

        return list;
    }
}