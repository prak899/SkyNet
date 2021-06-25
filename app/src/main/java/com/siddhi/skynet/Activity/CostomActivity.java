package com.siddhi.skynet.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nandroidex.upipayments.listener.PaymentStatusListener;
import com.nandroidex.upipayments.models.TransactionDetails;
import com.nandroidex.upipayments.utils.UPIPayment;
import com.siddhi.skynet.Class.OrderSheet;
import com.siddhi.skynet.Model.OrderModel;
import com.siddhi.skynet.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CostomActivity extends AppCompatActivity implements PaymentStatusListener {

    private TextView ServiceName, ServicePrice;
    private ImageView profileBack;
    FloatingActionButton fab;
    private Boolean profileSet = false;

    private FirebaseFirestore db;
    String productId, productPrice;


    String OrderId, coustomerNumber, orderDateTime;

    String status="Order";

    String discount="20%", coustomerAddress="Bilaspur, C.G", estimatedTime="within 1day";

    private UPIPayment upiPayment;
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
                CostomActivity.this.startUpiPayment();
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
                            Toast.makeText(CostomActivity.this, "Product Added", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CostomActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("Errrdsfgdj", "onFailure: "+e);
                        }
                    });

        }
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