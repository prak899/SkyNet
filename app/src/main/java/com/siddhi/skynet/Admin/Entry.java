package com.siddhi.skynet.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.siddhi.skynet.AdminModel.ServiceEntryModel;
import com.siddhi.skynet.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entry extends AppCompatActivity {
    Spinner spinner;
    String[] categorydrop = {"Select Category", "Instant", "with-in 2days", "In a week", "15 days min"};
    private TextInputEditText Name, Number;

    FloatingActionButton FabHome, FabDone;
    List<ServiceEntryModel> serviceEntryModels;
    DatabaseReference dbContact;

    CheckBox ImporantCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        init();

        serviceEntryModels = new ArrayList<>();
        dbContact = FirebaseDatabase.getInstance().getReference("serviceAD");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categorydrop);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        FabHome.setOnClickListener(v-> {
            startActivity(new Intent(this, ServiceEntry.class));
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FabDone.setOnClickListener(v-> {

            String name = Name.getText().toString();
            String numberContact = Number.getText().toString();

            if (name.isEmpty()){
                Toast.makeText(this, "Check name", Toast.LENGTH_SHORT).show();
            }else if (numberContact.isEmpty()){
                Toast.makeText(this, "Check number", Toast.LENGTH_SHORT).show();
            }else {
                addUser(name, numberContact);
            }
        });
    }

    private void init() {
        spinner= findViewById(R.id.spinner);
        FabHome= findViewById(R.id.fab1);

        //TextInputEditText Bindings
        Name= findViewById(R.id.address);
        Number= findViewById(R.id.pin);

        FabDone= findViewById(R.id.fab);

        ImporantCon = (CheckBox)findViewById(R.id.important);
    }

    private void addUser(String name, String numberContact) {

        Date date = new Date();


        if (!name.isEmpty() || numberContact.isEmpty()) {

            String id = dbContact.push().getKey();
            ServiceEntryModel serviceEntryModel = null;

            if (ImporantCon.isChecked()) {
                serviceEntryModel = new ServiceEntryModel(name, numberContact, id, true, spinner.getSelectedItem().toString());

            } else {
                serviceEntryModel = new ServiceEntryModel(name, numberContact, id, false, spinner.getSelectedItem().toString());

            }
            dbContact/*.child("serviceAD")*/.child(id).setValue(serviceEntryModel);
            Toast.makeText(Entry.this, "Service added", Toast.LENGTH_SHORT).show();
            /*startActivity(new Intent(Entry.this, Dashboard.class));*/
            Name.setText(null);
            Number.setText(null);

        } else {
            Toast.makeText(this, "Server error!", Toast.LENGTH_LONG).show();

        }
    }

}