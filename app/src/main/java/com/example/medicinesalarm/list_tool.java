package com.example.medicinesalarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import java.util.ArrayList;

public class list_tool extends AppCompatActivity {
//We used RecyclerView to display the data from our database
    private RecyclerView rv;
    private MedicineAdapter ma;
    private Mydatabase db;
    ArrayList<User_Data> users;
    ArrayList<Medicine> me;
    //To save only the medicines that the user had it by his id
    ArrayList<Medicine> person_me;
    public  final static int addr =1;
    int count,ID,c;
    String fname,lname,user_email,pass,cpass,ID_st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tool);

        //assign the variables
        person_me=new ArrayList<>();
        rv=findViewById(R.id.rv_med);
        db=new Mydatabase(this);
        users=db.getAllUsers();
        me  =db.getAllMedicine();
        //*********************************************//

        //The user id from the first activity
        Intent intent=getIntent();
        ID_st=intent.getStringExtra("IDString");
        ID=Integer.parseInt(ID_st);
        //*********************************************//

        //To return only the medicines that the user had it by his id
        for(c=0;c<me.size();c++)
        {
            if(me.get(c).getPerson_id()==ID)
            {

                person_me.add(me.get(c));
            }
        }
        //***********************************************//

        //pass the array list to the RecyclerView to view it
        ma= new MedicineAdapter(person_me);
        rv.setAdapter(ma);
        RecyclerView.LayoutManager lm=new GridLayoutManager(this,1);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

//User Profile Image
        ImageButton profile_btn = findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to retrieve the user data and store them in intent
                for(count=0;count<users.size();count++)
                {
                    if(users.get(count).getId()==ID)
                    {
                        fname=users.get(count).getFirstName();
                        lname = users.get(count).getLastName();
                        user_email = users.get(count).getEmail();
                        pass = users.get(count).getPassword();
                        cpass = users.get(count).getConfirmPassword();
                        Intent i =new Intent(getApplicationContext(),my_profile.class);
                        i.putExtra("U_ID",ID_st);
                        i.putExtra("fname",fname);
                        i.putExtra("lname",lname);
                        i.putExtra("user_email",user_email);
                        i.putExtra("pass",pass);
                        i.putExtra("cpass",cpass);
                        startActivity(i);
                    }
                }
            }
        });

//Log out Image
        ImageButton log_out_btn = findViewById(R.id.log_out_btn);
        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

//Add Medicine Button
        Button add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),add_medicine.class);
                i.putExtra("user_id",ID_st);
                startActivity(i);
            }
        });

//Update Button
        Button Update_btn = findViewById(R.id.Update_btn);
        Update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Update.class);
                i.putExtra("user_id",ID_st);
                startActivity(i);
            }
        });
    }

//required Method to save the data into the recyclerview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==addr&&resultCode==add_medicine.add)
        {
            ArrayList<Medicine> me  =db.getAllMedicine();
            ma.setM(me);
            ma.notifyDataSetChanged();

        }
    }
}