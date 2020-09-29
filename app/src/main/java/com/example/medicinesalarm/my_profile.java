package com.example.medicinesalarm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class my_profile extends AppCompatActivity {
    String fname,lname,email,pass,cpass,ID_st;
    int ID,count;
    Mydatabase db;
    ArrayList<User_Data> users;
    User_Data U;
    EditText pro_name,pro_em,pro_pa,pro_c_pa;
    Button btn_change;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        db=new Mydatabase(this);
        users=db.getAllUsers();
        check=false;

        //assign values for my_profile page
        pro_name=findViewById(R.id.My_profile_name);
        pro_em=findViewById(R.id.My_profile_mail);
        pro_pa=findViewById(R.id.My_profile_password);
        pro_c_pa=findViewById(R.id.My_profile_c_password);
        btn_change=findViewById(R.id.change_btn);

        Intent intent=getIntent();
        ID_st=intent.getStringExtra("U_ID");
        ID=Integer.parseInt(ID_st);
        fname=intent.getStringExtra("fname");
        lname=intent.getStringExtra("lname");
        email=intent.getStringExtra("user_email");
        pass=intent.getStringExtra("pass");
        cpass=intent.getStringExtra("cpass");

//Print user data before change
        pro_name.setText(fname+" "+lname);
        pro_em.setText(email);
        pro_pa.setText(pass);
        pro_c_pa.setText(cpass);

//Change Button
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FN,LN,E,P,CP;
                FN=pro_name.getText().toString();
                LN="";
                E=pro_em.getText().toString();
                P=pro_pa.getText().toString();
                CP=pro_c_pa.getText().toString();
                if(P.equals(CP)) {
                    for (count = 0; count < users.size(); count++) {
                        if (users.get(count).getId() == ID) {
                            check = true;
                            U = new User_Data(ID, FN,LN, E, P, CP);
                            if (db.Update_user(U) == true) {
                                Toast.makeText(getBaseContext(), "Successful Editing", Toast.LENGTH_LONG).show();
                                pro_name.setText(FN);
                                pro_em.setText(E);
                                pro_pa.setText(P);
                                pro_c_pa.setText(CP);
                            }
                        }
                    }
                    if (check == false) {
                        Toast.makeText(getBaseContext(), "Failed Editing ..! try again", Toast.LENGTH_LONG).show();
                        pro_name.setText(fname + " " + lname);
                        pro_em.setText(email);
                        pro_pa.setText(pass);
                        pro_c_pa.setText(cpass);
                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Password and Confirm password do not identically", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}