package com.siddhi.skynet.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.siddhi.skynet.AdminModel.ServiceEntryModel;
import com.siddhi.skynet.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Entry extends AppCompatActivity {
    Spinner spinner;
    String[] categorydrop = {"Select Category", "Instant", "with-in 2days", "In a week", "15 days min"};
    private TextInputEditText Name, Number;

    FloatingActionButton FabHome, FabDone;
    List<ServiceEntryModel> serviceEntryModels;
    DatabaseReference dbContact;

    CheckBox ImporantCon;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;
    CircleImageView addImg;

    String fileUrl;
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
            //startActivity(new Intent(this, ServiceEntry.class));
            uploadImage();
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
        addImg.setOnClickListener(v->{SelectImage();});
    }

    private void init() {
        spinner= findViewById(R.id.spinner);
        FabHome= findViewById(R.id.fab1);

        //TextInputEditText Bindings
        Name= findViewById(R.id.address);
        Number= findViewById(R.id.pin);

        FabDone= findViewById(R.id.fab);

        ImporantCon = (CheckBox)findViewById(R.id.important);
        addImg= findViewById(R.id.gallery_img_view);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void addUser(String name, String numberContact) {

        if (!name.isEmpty() || numberContact.isEmpty()) {

            String id = dbContact.push().getKey();
            ServiceEntryModel serviceEntryModel = null;

            if (ImporantCon.isChecked()) {
                serviceEntryModel = new ServiceEntryModel(name, numberContact, id, true, spinner.getSelectedItem().toString(), fileUrl);

            } else {
                serviceEntryModel = new ServiceEntryModel(name, numberContact, id, false, spinner.getSelectedItem().toString(), fileUrl);

            }
            dbContact.child(id).setValue(serviceEntryModel);
            Toast.makeText(Entry.this, "Service added", Toast.LENGTH_SHORT).show();
            Name.setText(null);
            Number.setText(null);

        } else {
            Toast.makeText(this, "Server error!", Toast.LENGTH_LONG).show();

        }
    }


    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage()
    {
        if (filePath != null) {
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            ref.putFile(filePath).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(Entry.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // getting image uri and converting into string
                                            Uri downloadUrl = uri;
                                            fileUrl = downloadUrl.toString();
                                            Log.d("XurlX", "onSuccess: uri= "+ fileUrl);
                                        }
                                    });

                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Entry.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
                    }
            }
}