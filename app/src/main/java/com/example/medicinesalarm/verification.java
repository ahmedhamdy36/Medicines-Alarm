package com.example.medicinesalarm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class verification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Button verify_btn = findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Successful Registration",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),waiting2.class);
                startActivity(i);
            }
        });
    }
}