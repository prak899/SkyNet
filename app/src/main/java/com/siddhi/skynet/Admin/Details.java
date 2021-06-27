package com.siddhi.skynet.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.siddhi.skynet.AdminModel.AdminModel;
import com.siddhi.skynet.AdminModel.ServiceEntryModel;
import com.siddhi.skynet.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Details extends AppCompatActivity {
    List<AdminModel> adminModels;
    DatabaseReference dbAddAdmin;
    String deviceToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

        adminModels = new ArrayList<>();
        dbAddAdmin = FirebaseDatabase.getInstance().getReference("Admin");


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                deviceToken = task.getResult();
            }
        });
    }

    public void init(){

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
}