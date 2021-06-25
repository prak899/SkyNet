package com.siddhi.skynet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.siddhi.skynet.Admin.ServiceEntry;
import com.siddhi.skynet.Model.Admin;
import com.siddhi.skynet.R;

public class Login extends AppCompatActivity {

    private DatabaseReference databaseReference;
    EditText Email, Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        init();

        databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://skynet-b0d35-default-rtdb.firebaseio.com/");
    }

    public void init(){
        Email= findViewById(R.id.et_email);
        Password= findViewById(R.id.et_password);
    }
    public void onClickBtn(View v)
    {
        Query query = databaseReference.child("users").orderByChild("email").equalTo(Email.getText().toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Admin admin = user.getValue(Admin.class);

                        if (admin.getPassword().equals(Password.getText().toString().trim())) {
                            SharedPreferences prefs= getSharedPreferences("loginPreef", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor= prefs.edit();
                            editor.putBoolean("admin", true);
                            editor.apply();

                            Intent intent = new Intent(Login.this, ServiceEntry.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Password is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}