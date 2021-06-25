package com.siddhi.skynet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.siddhi.skynet.Class.OrderSheet;
import com.siddhi.skynet.Model.OrderModel;
import com.siddhi.skynet.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CostomActivity extends AppCompatActivity {

    private TextView ServiceName, ServicePrice;
    private ImageView profileBack;
    FloatingActionButton fab;
    private Boolean profileSet = false;

    private FirebaseFirestore db;
    String productId, productName, productPrice, productDetails, productImage, productRating;


    String OrderId, coustomerNumber, orderDateTime;

    String storeType="Grocery", storeName="Prakash general store", status="Order";

    String discount="20%", coustomerAddress="Bilaspur, C.G", estimatedTime="within 1day";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costom);

        init();
        Intent intent = getIntent();
        String a = intent.getStringExtra("a");
        String b = intent.getStringExtra("b");
        String c = intent.getStringExtra("c");


        ServiceName.setText(a);
        ServicePrice.setText(b);

        fab.setOnClickListener(v->{
            Cick();
        });

        profileBack.setOnClickListener(v->{finish();});

    }

    public void Cick() {
        OrderSheet dialog = new OrderSheet(profileSet);
        dialog.showNow(getSupportFragmentManager(), OrderSheet.class.getSimpleName());
        dialog.setDialogClickListener(new OrderSheet.DialogClickListener() {
            @Override
            public void onDialogGalleryClick() {

                new SweetAlertDialog(CostomActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Succesfully ordered")
                        .setContentText("Will connect with you as soon as possible, Your order has been placed you can cancel it from order section.\n-Your order id is-\n" +OrderId)
                        .show();
                //orderProduct();
                saveProduct();
            }

            @Override
            public void onDialogCameraClick() {
                Toast.makeText(CostomActivity.this, "Order Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDialogRemoveClick() {
                //viewModel.removeProfilePicture(requireActivity().getApplicationContext());
            }
        });
    }

    private void init(){
        ServiceName= findViewById(R.id.servicename);
        ServicePrice= findViewById(R.id.servicePrice);
        fab= findViewById(R.id.fab);

        profileBack= findViewById(R.id.profileBack);
        db = FirebaseFirestore.getInstance();

    }
    private void saveProduct(){

        String a="0";
        if (a.equals("0")) {

            CollectionReference dbProducts = db.collection("Orders");

            CollectionReference dbProducts1 = db.collection("Orders11");
            OrderModel profile = new OrderModel(OrderId, productId, coustomerNumber, productPrice, discount, status,
                    OrderId, coustomerAddress, estimatedTime, orderDateTime);

            dbProducts.add(profile)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            dbProducts1.add(profile)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(CostomActivity.this, "Product Added", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CostomActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CostomActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }
}