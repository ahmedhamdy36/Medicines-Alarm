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

public class add_medicine extends AppCompatActivity {

    private ImageView iv;
    private TextView tv_n ,tv_ts,tv_t;
    final static int pick_image=1;
    public Uri U_image;
    private Button btn;
    private Mydatabase db;
    public  final static int add =3;
    String ID_st;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        iv=findViewById(R.id.iv_med);
        tv_n=findViewById(R.id.tv_Nmed);
        tv_ts=findViewById(R.id.tv_tsmed);
        tv_t=findViewById(R.id.tv_tmed);
        btn=findViewById(R.id.btn_me);
        db=new Mydatabase(this);

        //The user id from list_tool activity
        Intent intent=getIntent();
        ID_st=intent.getStringExtra("user_id");
        ID=Integer.parseInt(ID_st);
        //////////////////////////////////////////////

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

//Add Button
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
                else
                {
                    Medicine M=new Medicine(name,image,times,time,ID);
                    boolean res =db.insertMedicine(M);
                    int minute=0;

                    if(res)
                    {
                        Toast.makeText(getBaseContext(),"successfully added",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getBaseContext(),"This medicine name is already exist before please go to update it",Toast.LENGTH_LONG).show();
                    setResult(add,null);
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
                       // startActivity(intent);
                    }

                    Intent i = new Intent(getApplicationContext(), list_tool.class);
                    i.putExtra("IDString",ID_st);
                    startActivity(i);
                    //finish();
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