package com.siddhi.skynet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.siddhi.skynet.Adapter.CartAdapter;
import com.siddhi.skynet.Admin.Details;
import com.siddhi.skynet.AdminAdapter.AdminDataAdapter;
import com.siddhi.skynet.AdminModel.AdminDataModel;
import com.siddhi.skynet.AdminModel.AdminModel;
import com.siddhi.skynet.Class.OrderSheet;
import com.siddhi.skynet.Model.CartModel;
import com.siddhi.skynet.Model.OrderModel;
import com.siddhi.skynet.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CartActivity extends AppCompatActivity {
    private Boolean profileSet = false;
    DatabaseReference databaseReference;
    List<CartModel> cartModelList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    TextView EmptyView;
    ProgressBar progressBar;
    String usernum;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = getSharedPreferences("userData", Context.MODE_PRIVATE);
        usernum= prefs.getString("UserNumber", "0");

        showCartData();
    }

    public void init(){
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler);
        progressBar = (ProgressBar) findViewById(R.id.dotBounce);
        EmptyView = (TextView) findViewById(R.id.empty_view);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void showCartData(){
        databaseReference = FirebaseDatabase.getInstance().getReference("ServiceCart").child("55");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                cartModelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartModel cartModel = dataSnapshot.getValue(CartModel.class);
                    cartModelList.add(cartModel);

                }
                adapter = new CartAdapter(cartModelList, CartActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (cartModelList.isEmpty()) {
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

    public void goWithCartOrder(){
        databaseReference = FirebaseDatabase.getInstance().getReference("ServiceCart").child(usernum);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();


                    String serviceType = dataSnapshot.child("serviceType").getValue(String.class);
                    String userAddress = dataSnapshot.child("userAddress").getValue(String.class);
                    String esimateTime = dataSnapshot.child("esimateTime").getValue(String.class);
                    String servicePrice  = dataSnapshot.child("servicePrice").getValue(String.class);
                    String userEmail   = dataSnapshot.child("userEmail").getValue(String.class);
                    String serviceName   = dataSnapshot.child("serviceName").getValue(String.class);
                    String orderDate    = dataSnapshot.child("orderDate").getValue(String.class);
                    String userNumber      = dataSnapshot.child("userNumber").getValue(String.class);
                    String orderQuantity    = dataSnapshot.child("orderQuantity").getValue(String.class);
                    String paymentStatus      = dataSnapshot.child("paymentStatus").getValue(String.class);
                    String serviceImage      = dataSnapshot.child("serviceImage").getValue(String.class);


                    Log.d("XmapX", "onDataChange: "+map);


                    TextView totalAmount = (TextView)findViewById(R.id.totalAmount);
                    totalAmount.setText("Total: ");

                    saveServiceBook(serviceType, userAddress, esimateTime, servicePrice,
                            userEmail, serviceName, orderDate, userNumber,
                            orderQuantity, paymentStatus, serviceImage);
                }

                Toast.makeText(CartActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                deleteCart(usernum);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void CheckOut(View view){
        Cick();
    }

    public void Cick() {
        OrderSheet dialog = new OrderSheet(profileSet);
        dialog.showNow(getSupportFragmentManager(), OrderSheet.class.getSimpleName());
        dialog.setDialogClickListener(new OrderSheet.DialogClickListener() {
            @Override
            public void onDialogGalleryClick() {
                //saveServiceBook();
                goWithCartOrder();
            }

            @Override
            public void onDialogCameraClick() {


            }

            @Override
            public void onDialogRemoveClick() {

            }
        });
    }


    private void saveServiceBook(String serviceType,String userAddress,String esimateTime,String servicePrice,
                                 String userEmail,String serviceName,String orderDate,String userNumber,
                                 String orderQuantity,String paymentStatus, String serviceImage){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Booking");
        String  currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        CollectionReference dbProducts = db.collection("Orders");

        OrderModel orderModel = new OrderModel(serviceName, servicePrice, paymentStatus, serviceType, userNumber, userEmail,
                userAddress, esimateTime, currentDateTimeString, String.valueOf(orderQuantity), serviceImage);

        dbProducts.add(orderModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        /*new SweetAlertDialog(CartActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Service Booked")
                                .setContentText("Will connect with you as soon as possible, Your service has been placed you can cancel it from order section.")
                                .show();
                        Snackbar.make(findViewById(R.id.rootLayoutCart), "Service Booked", Snackbar.LENGTH_SHORT).show();*/
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.rootLayout), "Server error! Try again", Snackbar.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Log.d("Errrdsfgdj", "onFailure: "+e);
                    }
                });
    }

    private void deleteCart(String userNumber){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ServiceCart");
        ref.child(userNumber).orderByChild(userNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            ds.getRef().removeValue();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(CartActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}