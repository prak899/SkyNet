package com.siddhi.skynet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukesh.OtpView;
import com.siddhi.skynet.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Otp extends AppCompatActivity {

    private String mVerificationId;
    private FirebaseAuth mAuth;
    String phoneNo;
    private OtpView otpView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpView = findViewById(R.id.otp_view);
        progressBar= findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


        phoneNo = getIntent().getStringExtra("phoneNo");

        sendVerificationCode(phoneNo);
    }

    public void verify(View view){
        progressBar.setVisibility(View.VISIBLE);
        String code = otpView.getText().toString();
        Log.d("XotpX", code);
        if (code.isEmpty() || code.length() < 6) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.otp_lay), "Please Enter OTP", Snackbar.LENGTH_LONG);
            snackbar.setAction("OK", v1 -> {

            });
            snackbar.show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        //verifying the code entered manually
        verifyVerificationCode(code);
    }



    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        progressBar.setVisibility(View.GONE);
    }


    //the callback to detect the verification status
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                otpView.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Otp.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("XlogX", e.getMessage());
            Snackbar snackbar = Snackbar.make(findViewById(R.id.otp_lay), e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.setAction("Resend", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendVerificationCode(phoneNo);
                }
            });
            snackbar.show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Otp.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            Intent intent = new Intent(Otp.this, ProfileSetup.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";
                            progressBar.setVisibility(View.GONE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                                progressBar.setVisibility(View.GONE);
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.otp_lay), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Call me
                                }
                            });
                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }
}