package com.example.medicinesalarm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class waiting2 extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginintent = new Intent(waiting2.this,MainActivity.class);
                startActivity(loginintent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}