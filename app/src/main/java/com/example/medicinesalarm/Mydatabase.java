package com.example.medicinesalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class Mydatabase extends SQLiteOpenHelper {
    public static final String Name="MedicineAlarm_db";
    public static int version =3;

    //table name
    public static final String m ="medicine";
    //table values
    public static final String n ="name";
    public static final String im ="image";
    public static final String ID ="id";
    public static final String ts="times";
    public static final String t ="time";
    public static final String Person_id="Person_ID";

    //table name
    public static final String p="Person";
    //table values
    public static final String P_ID ="id";
    public static final String P_FirstName ="FirstName";
    public static final String P_lastName="LastName";
    public static final String P_mail ="Email";
    public static final String P_pass ="Password";
    public static final String P_Cpass ="ConfirmPassword";

    public Mydatabase(@Nullable Context context) {
        super(context, Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables

        db.execSQL("create table "+m+"("+ID+","+n+"  text UNIQUE,"+im+" text,"+ts+" integer ,"+t+" integer , "+Person_id+" intger not null , FOREIGN KEY (\"+Person_id+\") REFERENCES \"+p+\" (\"+P_ID+\"));");
        db.execSQL("create table "+p+"("+P_ID+" integer primary key autoincrement ,"+P_FirstName+"  text ,"+P_lastName+" text,"+P_mail+" text UNIQUE ,"+P_pass+" text ,"+P_Cpass+" text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //remove the old tables and update the database
        db.execSQL(" drop table if exists "+ m);
        db.execSQL(" drop table if exists "+ p);
        onCreate(db);
    }

//***********************************************************
//Medicine methods:
//***********************************************************

//This method to insert a new medicine
    public boolean insertMedicine(Medicine med)
    {
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(n,med.getName());
        //Bitmap bitmap=med.getImage();
        //byte[] image=med.get_bytes(bitmap);
        values.put(im,med.getImage());
        values.put(ts,med.getTimes());
        values.put(t,med.getTime());
        values.put(Person_id,med.getPerson_id());
        long l=db.insert(m,null,values);
        return  l!=-1;
    }

//This method to update old medicine
    public boolean updateMedicine(Medicine med)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values =new ContentValues();

        //Bitmap bitmap=med.getImage();
        //byte[] image=med.get_bytes(bitmap);
        //Uri uri;

        values.put(n,med.getName());
        values.put(im,med.getImage());
        values.put(ts,med.getTimes());
        values.put(t,med.getTime());
        values.put(Person_id,med.getPerson_id());
        String args [] = {med.getName()+""};
        int l=db.update(m,values,"name=?",args);
        return  l>0;
    }

//This method to delete a medicine
    public boolean deleteMedicine(Medicine med)
    {
        SQLiteDatabase db=getWritableDatabase();
        String args [] = {med.getId()+""};
        int l=db.delete(m,"id=?",args);
        return  l>0;
    }

//This method to return all the medicines
    public ArrayList<Medicine> getAllMedicine()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery(" select * from " +m ,null);
        ArrayList<Medicine> med =new ArrayList<>();
        if (c!=null && c.moveToFirst()){
            do{
                int id =c.getInt(c.getColumnIndex(ID));
                String name =c.getString(c.getColumnIndex(n));
                String image=c.getString(c.getColumnIndex(im));
                int times=c.getInt(c.getColumnIndex(ts));
                int time =c.getInt(c.getColumnIndex(t));
                int personID =c.getInt(c.getColumnIndex(Person_id));
                Medicine me =new Medicine(id,name,image,times,time,personID);
                med.add(me);
            }while(c.moveToNext());
            c.close();
        }
        return med;
    }

//******************************************************************
//User methods:
//******************************************************************

//This method to insert a new user
    public boolean insertUser(User_Data user)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(P_FirstName,user.getFirstName());
        values.put(P_lastName,user.getLastName());
        values.put(P_mail,user.getEmail());
        values.put(P_pass,user.getPassword());
        values.put(P_Cpass,user.getConfirmPassword());
        long l=db.insert(p,null,values);
        return  l!=-1;
    }

//Update user method but we did not use it
    public boolean Update_user(User_Data user)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(P_FirstName,user.getFirstName());
        values.put(P_lastName,user.getLastName());
        values.put(P_mail,user.getEmail());
        values.put(P_pass,user.getPassword());
        values.put(P_Cpass,user.getConfirmPassword());
        String args[]={user.getId()+""};
        int l=db.update(p,values,"id=?",args);
        return  l>0;
    }

//to retrieve all users
    public ArrayList<User_Data> getAllUsers()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery(" select * from " +p ,null);
        ArrayList<User_Data> user =new ArrayList<>();
        if (c!=null && c.moveToFirst()){
            do{
                int id =c.getInt(c.getColumnIndex(P_ID));
                String firstname =c.getString(c.getColumnIndex(P_FirstName));
                String lastname=c.getString(c.getColumnIndex(P_lastName));
                String email =c.getString(c.getColumnIndex(P_mail));
                String pass=c.getString(c.getColumnIndex(P_pass));
                String cpass =c.getString(c.getColumnIndex(P_Cpass));
                User_Data u =new User_Data(id,firstname,lastname,email,pass ,cpass);
                user.add(u);
            }while(c.moveToNext());
            c.close();
        }
        return user;
    }
}