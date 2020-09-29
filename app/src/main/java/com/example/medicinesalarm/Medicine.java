package com.example.medicinesalarm;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

public class Medicine {
    private int id;
    private String name ;
    private String image;
    private int times;
    private int time;
    private int Person_id;

    public Medicine(int id, String name, String image, int times, int time) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.times = times;
        this.time = time;
    }

    public Medicine( String name, String image, int times, int time) {
        this.name = name;
        this.image = image;
        this.times = times;
        this.time = time;
    }

    public Medicine( String name, String image, int times, int time,int person_id) {
        this.name = name;
        this.image = image;
        this.times = times;
        this.time = time;
        this.Person_id=person_id;
    }

    public Medicine( int id,String name, String image, int times, int time,int person_id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.times = times;
        this.time = time;
        this.Person_id=person_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPerson_id(int person_id) {
        Person_id = person_id;
    }

    public int getPerson_id() {
        return Person_id;
    }

    public byte[] get_bytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }
}