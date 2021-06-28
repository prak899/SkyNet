package com.siddhi.skynet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.siddhi.skynet.R;

public class SignIn extends AppCompatActivity {
    TextInputEditText User_Number;
    View shine;
    TextView Admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        init();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds
                handler.postDelayed(this, 2000);
                shineAnimation();
            }
        }, 2000);  //the time is in miliseconds

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Admin.setOnClickListener(v->{
            startActivity(new Intent(this, Login.class));
        });
    }

    private void init() {
        User_Number= findViewById(R.id.number);

        shine = findViewById(R.id.shine);

        Admin= findViewById(R.id.admin);
        shineAnimation();
    }

    private void shineAnimation() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.left_right);
        shine.startAnimation(anim);


    }

    public void signup(View v){
        String phoneNo = User_Number.getText().toString();
        Intent intent = new Intent(getApplicationContext(),Otp.class);
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);
    }
}