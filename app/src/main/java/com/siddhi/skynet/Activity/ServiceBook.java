package com.siddhi.skynet.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.siddhi.skynet.Adapter.MasterAdapter;
import com.siddhi.skynet.Model.MasterModel;
import com.siddhi.skynet.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServiceBook extends AppCompatActivity {

    DatabaseReference databaseReference;
    List<MasterModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    TextView EmptyView, HeaderName;
    ProgressBar progressBar;


    FloatingActionButton fab;
    ImageView cartOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_book);

        init();
        seoM();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> fab.setEnabled(isChecked));

        fab.setOnClickListener(v->{


        });
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.promoter_recycler);
        progressBar = (ProgressBar) findViewById(R.id.dotBounce);
        EmptyView = (TextView) findViewById(R.id.empty_view);

        HeaderName= findViewById(R.id.header_name);

        fab= findViewById(R.id.fab);
        cartOption= findViewById(R.id.cartOption);
    }
    public void seoM(){

        databaseReference = FirebaseDatabase.getInstance().getReference("serviceAD")/*.child("AD")*/;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Log.d("XtrueX", "onDataChange: "+snapshot.getChildrenCount());

                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MasterModel grocery = dataSnapshot.getValue(MasterModel.class);
                    list.add(grocery);
                }
                adapter = new MasterAdapter(list, ServiceBook.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (list.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    EmptyView.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    EmptyView.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}