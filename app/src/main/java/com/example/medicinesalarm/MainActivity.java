package com.example.medicinesalarm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

//call all users from my database
    Mydatabase db;
    ArrayList<User_Data> users;
    boolean check;
    int count,ID;
    String ID_st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign the database object and users array
        db=new Mydatabase(this);
        users=db.getAllUsers();
        check=false;

//Login Button
        Button login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText email=findViewById(R.id.email);
                EditText password=findViewById(R.id.password);
                String E=email.getText().toString();
                String P=password.getText().toString();

                for(count=0;count<users.size();count++)
                {
                    if(users.get(count).getEmail().equals(E)&&users.get(count).getPassword().equals(P))
                    {
                        //Before enter the next activity we had saved the user id in the intent
                        check = true;
                        ID = users.get(count).getId();
                        ID_st=String.valueOf(ID);
                        Intent i = new Intent(getApplicationContext(), list_tool.class);
                        i.putExtra("IDString",ID_st);
                        startActivity(i);
                        finish();
                    }
                }

                if(check==false)
                {
                    Toast.makeText(getBaseContext(),"Email or Password does not exist..! try again",Toast.LENGTH_LONG).show();
                    password.setText("");
                }
            }
        });

//About Button
        ImageButton about_btn = findViewById(R.id.about_btn);
        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),about.class);
                startActivity(i);
            }
        });

//Register Button
        Button register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),registration.class);
                startActivity(i);
            }
        });

//Forget Button
        TextView forget_password_tv = (TextView)findViewById(R.id.forget_password_tv);
        forget_password_tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),forget_password.class);
                startActivity(i);
            }
        });
    }
}