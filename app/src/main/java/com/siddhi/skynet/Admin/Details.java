package com.siddhi.skynet.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.siddhi.skynet.Activity.ServiceBook;
import com.siddhi.skynet.Adapter.MasterAdapter;
import com.siddhi.skynet.AdminAdapter.AdminDataAdapter;
import com.siddhi.skynet.AdminModel.AdminDataModel;
import com.siddhi.skynet.AdminModel.AdminModel;
import com.siddhi.skynet.AdminModel.ServiceEntryModel;
import com.siddhi.skynet.Model.MasterModel;
import com.siddhi.skynet.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Details extends AppCompatActivity {
    List<AdminModel> adminModels;
    DatabaseReference dbAddAdmin;
    String deviceToken;
    DatabaseReference databaseReference;
    List<AdminDataModel> adminDataModels = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    TextView EmptyView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

        adminModels = new ArrayList<>();
        dbAddAdmin = FirebaseDatabase.getInstance().getReference("Admin");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                deviceToken = task.getResult();
            }
        });
        AdminData();
    }

    public void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.dotBounce);
        EmptyView = (TextView) findViewById(R.id.empty_view);
    }

    public void showAlertDialogButtonClicked(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new admin");

        final View customLayout = getLayoutInflater().inflate(R.layout.new_admin, null);
        builder.setView(customLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String username, password;
                                TextInputEditText editText = customLayout.findViewById(R.id.tiet_username);
                                TextInputEditText editText1 = customLayout.findViewById(R.id.tiet_password);

                                username= editText.getText().toString();
                                password= editText1.getText().toString();
                                if (username.isEmpty())
                                    Toast.makeText(Details.this, "Check username", Toast.LENGTH_SHORT).show();
                                else if (password.isEmpty())
                                    Toast.makeText(Details.this, "Check password", Toast.LENGTH_SHORT).show();
                                else
                                addAdmin(username, password);

                            }
                        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void Back(View view){
        this.finish();
    }

    private void addAdmin(String userId, String password) {

        if (!userId.isEmpty() || password.isEmpty()) {

            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            String id = dbAddAdmin.push().getKey();
            AdminModel adminModel = null;

            adminModel = new AdminModel(userId, password, currentDateTimeString, deviceToken, true);

            dbAddAdmin.child(id).setValue(adminModel);
            Toast.makeText(this, "New admin added", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Server error!", Toast.LENGTH_LONG).show();

        }
    }


    public void AdminData(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                adminDataModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AdminDataModel adminDataModel = dataSnapshot.getValue(AdminDataModel.class);
                    adminDataModels.add(adminDataModel);
                }
                adapter = new AdminDataAdapter(adminDataModels, Details.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (adminDataModels.isEmpty()) {
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
                EmptyView.setVisibility(View.VISIBLE);
            }
        });
    }
}