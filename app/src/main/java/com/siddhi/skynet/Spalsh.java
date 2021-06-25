package com.siddhi.skynet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.siddhi.skynet.Activity.ServiceBook;
import com.siddhi.skynet.Activity.SignIn;
import com.siddhi.skynet.Admin.ServiceEntry;


public class Spalsh extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    boolean bool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        TextView textView= findViewById(R.id.tv_splash_app_title);
        TextView textView1= findViewById(R.id.tv_splash_app_version);

        textView1.setText("V 1.0.0");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        firebaseAuth = FirebaseAuth.getInstance();


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            int count = 0;

            @Override
            public void run() {
                count++;

                if (count == 1)
                {
                    Load();
                    textView.setText("Loading.");
                }
                else if (count == 2)
                {
                    textView.setText("Loading..");
                }
                else if (count == 3)
                {
                    textView.setText("Loading...");
                }

                if (count == 3)


                handler.postDelayed(this, 2 * 1000);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


        SharedPreferences prefs = getSharedPreferences("loginPreef", Context.MODE_PRIVATE);
        bool= prefs.getBoolean("admin", false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }


    public void Load(){
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(Spalsh.this, ServiceBook.class));
            Spalsh.this.finish();
        } else if (bool){
            startActivity(new Intent(Spalsh.this, ServiceEntry.class));
        } else {
            startActivity(new Intent(Spalsh.this, SignIn.class));
        }
    }

}