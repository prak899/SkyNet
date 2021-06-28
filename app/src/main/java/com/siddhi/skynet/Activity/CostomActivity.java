package com.siddhi.skynet.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nandroidex.upipayments.listener.PaymentStatusListener;
import com.nandroidex.upipayments.models.TransactionDetails;
import com.nandroidex.upipayments.utils.UPIPayment;
import com.siddhi.skynet.Admin.ProfileModel;
import com.siddhi.skynet.Admin.ServiceEntry;
import com.siddhi.skynet.Class.OrderSheet;
import com.siddhi.skynet.Model.CartModel;
import com.siddhi.skynet.Model.OrderModel;
import com.siddhi.skynet.R;
import com.siddhi.skynet.Spalsh;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CostomActivity extends AppCompatActivity implements PaymentStatusListener {

    private TextView ServiceName, ServicePrice;
    private ImageView profileBack, serviceImageView;

    private Boolean profileSet = false;

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    String userNumber, userAddress, userEmailAddress, userName;
    String serviceName, servicePrice, serviceType, serviceImage;

    String estimatedTime="within 1day";
    String paymentStatus="Cash";

    private UPIPayment upiPayment;

    LinearLayout personalinfo, experience;
    TextView personalinfobtn, experiencebtn;
    int quantity = 1;
    DatabaseReference dbCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costom);

        init();
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.GONE);

        Intent intent = getIntent();
        serviceName = intent.getStringExtra("a");
        servicePrice = intent.getStringExtra("b");
        serviceType = intent.getStringExtra("c");
        serviceImage = intent.getStringExtra("d");


        SharedPreferences prefs = getSharedPreferences("userData", Context.MODE_PRIVATE);
        userNumber= prefs.getString("UserNumber", "0");
        userName= prefs.getString("UserName", "0");
        userEmailAddress= prefs.getString("UserEmailAddress", "0");
        userAddress= prefs.getString("UserAddress", "0");


        ServiceName.setText(serviceName);
        ServicePrice.setText("Rs "+servicePrice);
        if (serviceImage != null) {
            Glide.with(this).load(serviceImage).into(serviceImageView);

        }else {
            Glide.with(this).load(R.mipmap.ic_launcher).into(serviceImageView);

        }

        profileBack.setOnClickListener(v->{finish();});

        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);
                experience.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));
                experiencebtn.setTextColor(getResources().getColor(R.color.grey));


            }
        });

        experiencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.VISIBLE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                experiencebtn.setTextColor(getResources().getColor(R.color.blue));

            }
        });

    }

    public void increaseInteger(View view) {
        quantity = quantity + 1;
        display(quantity);

    }public void decreaseInteger(View view) {
        quantity = quantity - 1;
        display(quantity);
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.prnumber);
        displayInteger.setText("" + number);
    }
    public void Cick() {
        OrderSheet dialog = new OrderSheet(profileSet);
        dialog.showNow(getSupportFragmentManager(), OrderSheet.class.getSimpleName());
        dialog.setDialogClickListener(new OrderSheet.DialogClickListener() {
            @Override
            public void onDialogGalleryClick() {
                saveServiceBook();
            }

            @Override
            public void onDialogCameraClick() {
                CostomActivity.this.startUpiPayment();

            }

            @Override
            public void onDialogRemoveClick() {

            }
        });
    }

    private void init(){
        ServiceName= findViewById(R.id.servicename);
        ServicePrice= findViewById(R.id.servicePrice);

        profileBack= findViewById(R.id.profileBack);
        serviceImageView= findViewById(R.id.serviceImageView);

        personalinfo = findViewById(R.id.personalinfo);
        experience = findViewById(R.id.experience);

        personalinfobtn = findViewById(R.id.personalinfobtn);
        experiencebtn = findViewById(R.id.experiencebtn);


        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        dbCart = FirebaseDatabase.getInstance().getReference("ServiceCart");
    }
    public void serviceBook(View view){
        if (firebaseAuth.getCurrentUser() != null) {
            Cick();

        } else {
            startActivity(new Intent(this, SignIn.class));
        }
    }
    public void addToCart(View view){
        if (firebaseAuth.getCurrentUser() != null) {
            SharedPreferences prefs= getSharedPreferences("serviceCartPrice", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= prefs.edit();
            editor.putString("servicePrice", servicePrice);

            editor.apply();
            saveToCart();

        } else {
            startActivity(new Intent(this, SignIn.class));
        }
    }
    private void saveServiceBook(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Booking");
            String  currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            CollectionReference dbProducts = db.collection("Orders");

            OrderModel orderModel = new OrderModel(serviceName, servicePrice, paymentStatus, serviceType, userNumber, userEmailAddress,
                    userAddress, estimatedTime, currentDateTimeString, String.valueOf(quantity), serviceImage);

            dbProducts.add(orderModel)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            new SweetAlertDialog(CostomActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Service Booked")
                                    .setContentText("Will connect with you as soon as possible, Your service has been placed you can cancel it from order section.")
                                    .show();
                            Snackbar.make(findViewById(R.id.rootLayout), "Service Booked", Snackbar.LENGTH_SHORT).show();
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


    private void saveToCart() {

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

            String id = dbCart.push().getKey();
            CartModel cartModel = null;

            cartModel = new CartModel(serviceName, servicePrice, paymentStatus, serviceType, userNumber, userEmailAddress,
                    userAddress, estimatedTime, currentDateTimeString, String.valueOf(quantity), serviceImage);


            dbCart.child(userNumber).child(id).setValue(cartModel);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CartActivity.class));
    }

    private void startUpiPayment() {
        long millis = System.currentTimeMillis();
        upiPayment = new UPIPayment.Builder()
                .with(CostomActivity.this)
                .setPayeeVpa(getString(R.string.vpa))
                .setPayeeName(getString(R.string.payee))
                .setTransactionId(Long.toString(millis))
                .setTransactionRefId(Long.toString(millis))
                .setDescription(getString(R.string.transaction_description))
                .setAmount(getString(R.string.amount))
                .build();

        upiPayment.setPaymentStatusListener(this);

        if (upiPayment.isDefaultAppExist()) {
            onAppNotFound();
            return;
        }

        upiPayment.startPayment();
    }

    @Override
    public void onTransactionCompleted(@Nullable TransactionDetails transactionDetails) {
        String status = null;
        String approvalRefNo = null;
        if (transactionDetails != null) {
            status = transactionDetails.getStatus();
            approvalRefNo = transactionDetails.getApprovalRefNo();
        }
        boolean success = false;
        if (status != null) {
            success = status.equalsIgnoreCase("success") || status.equalsIgnoreCase("submitted");
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        /*int dialogType = success ? DialogTypes.TYPE_SUCCESS : DialogTypes.TYPE_ERROR;
        String title = success ? "Good job!" : "Oops!";
        String description = success ? ("UPI ID :" + approvalRefNo) : "Transaction Failed/Cancelled";
        int buttonColor = success ? Color.parseColor("#00C885") : Color.parseColor("#FB2C56");
        LottieAlertDialog alertDialog = new LottieAlertDialog.Builder(this, dialogType)
                .setTitle(title)
                .setDescription(description)
                .setNoneText("Okay")
                .setNoneTextColor(Color.WHITE)
                .setNoneButtonColor(buttonColor)
                .setNoneListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                    }
                })
                .build();
        alertDialog.setCancelable(false);
        alertDialog.show();
        upiPayment.detachListener();*/

    }

    @Override
    public void onAppNotFound() {
        Toast.makeText(this, "App Not Found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionSubmitted() {
        Toast.makeText(this, "Pending | Submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

}