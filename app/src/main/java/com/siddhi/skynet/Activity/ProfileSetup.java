package com.siddhi.skynet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.siddhi.skynet.Admin.ProfileModel;
import com.siddhi.skynet.R;

import java.util.Date;
import java.util.List;

public class ProfileSetup extends AppCompatActivity {

    private EditText Name, Number, FullAddress, EmailAddress;

    List<ProfileSetup> profileSetups;
    DatabaseReference dbContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        init();

        dbContact = FirebaseDatabase.getInstance().getReference("ServiceUsers");
    }

    private void init(){
        Name= findViewById(R.id.et_name);
        Number= findViewById(R.id.et_number);
        FullAddress= findViewById(R.id.et_address);
        EmailAddress= findViewById(R.id.et_email);

    }

    public void Register(View view){
        String name = Name.getText().toString();
        String number = Number.getText().toString();
        String fullAddresses= FullAddress.getText().toString();
        String emailAddress= EmailAddress.getText().toString();


        if (name.isEmpty())
            Toast.makeText(this, "Check name", Toast.LENGTH_SHORT).show();
        else if (number.isEmpty())
            Toast.makeText(this, "Check number", Toast.LENGTH_SHORT).show();
        else if (fullAddresses.isEmpty())
            Toast.makeText(this, "Check address", Toast.LENGTH_SHORT).show();
        else if (emailAddress.isEmpty())
            Toast.makeText(this, "Check emailAddress", Toast.LENGTH_SHORT).show();
        else
            addUser(name, number, fullAddresses, emailAddress);

    }

    private void addUser(String name, String number, String emailAddress, String fullAddress) {

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());



        if (!name.isEmpty() || number.isEmpty() || emailAddress.isEmpty() || fullAddress.isEmpty()) {

            String id = dbContact.push().getKey();
            ProfileModel profileModel = null;


            profileModel = new ProfileModel(name, number, fullAddress, emailAddress, currentDateTimeString);


            dbContact.child(id).setValue(profileModel);
            Toast.makeText(ProfileSetup.this, "You are all setup", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileSetup.this, ServiceBook.class));

            Name.setText(null);
            Number.setText(null);

        } else {
            Toast.makeText(this, "Server error!", Toast.LENGTH_LONG).show();

        }
    }

}