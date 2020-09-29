package com.example.medicinesalarm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Register class to save the user data into the database
public class registration extends AppCompatActivity {
    private EditText tv_Fname ,tv_Lname , tv_email ,tv_pass, tv_cpass;
    Mydatabase db=new Mydatabase(this);
    User_Data User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        tv_Fname=findViewById(R.id.first_name);
        tv_Lname=findViewById(R.id.second_name);
        tv_email=findViewById(R.id.Register_mail);
        tv_pass=findViewById(R.id.Register_password);
        tv_cpass=findViewById(R.id.Register_c_password);

//Create account button
        Button creat_account_btn =findViewById(R.id.creat_account_btn);
        creat_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_pass.getText().toString().equals(tv_cpass.getText().toString())) {
                    String Fname= tv_Fname.getText().toString();
                    String Lname= tv_Lname.getText().toString();
                    String email= tv_email.getText().toString();
                    String pass= tv_pass.getText().toString();
                    String cpass= tv_cpass.getText().toString();
                    User=new User_Data(Fname,Lname,email,pass,cpass);
                    if(db.insertUser(User)) {
                        Intent l = new Intent(getApplicationContext(), verification.class);
                        startActivity(l);
                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Password and Confirm password do not identically",Toast.LENGTH_LONG).show();
                    tv_pass.setText("");
                    tv_cpass.setText("");
                }
            }
        });
    }
}