package com.example.medicinesalarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Update extends AppCompatActivity {
    private ImageView iv;
    private TextView tv_n ,tv_ts,tv_t;
    final static int pick_image=1;
    public Uri U_image;
    private Button btn;
    private Mydatabase db;
    ArrayList<Medicine> medicines;
    ArrayList<Medicine> person_medicine;
    public  final static int add =3;
    String ID_st;
    int ID,count,c,medicine_id;
    Medicine M;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        db=new Mydatabase(this);
        medicines=db.getAllMedicine();
        person_medicine=new ArrayList<>();
        iv=findViewById(R.id.iv_med);
        tv_n=findViewById(R.id.tv_Nmed);
        tv_ts=findViewById(R.id.tv_tsmed);
        tv_t=findViewById(R.id.tv_tmed);
        btn=findViewById(R.id.btn_me);
        check=false;

        //The user id from list_tool activity
        Intent intent=getIntent();
        ID_st=intent.getStringExtra("user_id");
        ID=Integer.parseInt(ID_st);
        //*******************************************

        //To return only the medicines that the user had it by his id
        for(c=0;c<medicines.size();c++)
        {
            if(medicines.get(c).getPerson_id()==ID)
            {
                person_medicine.add(medicines.get(c));
            }
        }
        //***********************************************

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent I=new Intent(Intent.ACTION_PICK);
                Uri data=Uri.parse(Environment.getExternalStorageState());
                I.setDataAndType(data,"image/*");
                startActivityForResult(I,1);
                //  startActivityForResult(in,pick_image);
            }
        });

//Update Button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,image;
                int times ,time ;
                name=tv_n.getText().toString();
                image=U_image.toString();
                times=Integer.parseInt(tv_ts.getText().toString());
                time=Integer.parseInt(tv_t.getText().toString());
                if(times>3)
                {
                    Toast.makeText(getBaseContext(),"Error please enter 1 or 2 or 3 times ",Toast.LENGTH_LONG).show();
                }
                else{
                    for(count=0;count<person_medicine.size();count++)
                    {
                        if(name.equals(person_medicine.get(count).getName()))
                        {
                            check=true;
                            medicine_id=person_medicine.get(count).getId();
                            M=new Medicine(medicine_id,name,image,times,time,ID);
                            boolean res =db.updateMedicine(M);
                            if(res)
                            {
                                Toast.makeText(getBaseContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                            }
                            //External Alarm
                            int minute=0;
                            Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
                            if(times==1)
                            {
                                intent.putExtra(AlarmClock.EXTRA_HOUR,time);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                            }
                            else if(times==2)
                            {
                                intent.putExtra(AlarmClock.EXTRA_HOUR,time);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                                intent.putExtra(AlarmClock.EXTRA_HOUR,time+12);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                            }
                            else if(times==3)
                            {
                                intent.putExtra(AlarmClock.EXTRA_HOUR,time);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                                intent.putExtra(AlarmClock.EXTRA_HOUR,time+8);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                                intent.putExtra(AlarmClock.EXTRA_HOUR,time+16);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                            }
                            if(time<=24&&minute<=60) {
                                //startActivity(intent);
                            }
                            Intent i = new Intent(getApplicationContext(),list_tool.class);
                            i.putExtra("IDString",ID_st);
                            startActivity(i);
                            //finish();
                        }
                    }

                    if(check==false)
                    {
                        Toast.makeText(getBaseContext(),"The medicine name dose not exist ..!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_image&&resultCode==RESULT_OK){
            if(data!=null)
            {
                U_image=data.getData();
                iv.setImageURI(U_image);
            }

            //InputStream inputStream;
            //try {
            //  inputStream=getContentResolver().openInputStream(U_image);
            //Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
            //iv.setImageBitmap(bitmap);
            //}catch (Exception ex){
            //  ex.printStackTrace();
            //}
        }
    }
}