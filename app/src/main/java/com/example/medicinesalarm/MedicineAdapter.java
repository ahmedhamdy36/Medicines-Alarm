package com.example.medicinesalarm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.ArrayList;

//Adapter class that organize the RecycledView data
public class MedicineAdapter  extends RecyclerView.Adapter< MedicineAdapter.MedicineHolder> {
    private ArrayList<Medicine> m;
    Bitmap bitmap;

    public MedicineAdapter(ArrayList<Medicine> m) {
        this.m=m;
    }

    public ArrayList<Medicine> getM() {
        return m;
    }

    public void setM(ArrayList<Medicine> m) {
        this.m = m;
    }

    //onCreateViewHolder method
    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mecicine_adding,null,false);
        MedicineHolder M=new MedicineHolder(v);
        return M;
    }

    //onBindViewHolder method
    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, final int position) {
        Medicine med=m.get(position);

        //some ways to save the image
        //Picasso.get().load(uri).into(holder.iv);
        //holder.iv.setImageURI(Uri.parse(med.getImage()));

        String uri=med.getImage();
        bitmap=null;
        try {
            InputStream str=new java.net.URL(uri).openStream();
             bitmap=BitmapFactory.decodeStream(str);
             holder.iv.setImageBitmap(bitmap);

        }catch (Exception ex){ ex.printStackTrace(); }

        holder.tv_N.setText(med.getName());
        holder.tv_ts.setText(med.getTimes()+"");
        holder.tv_T1.setText(med.getTime()+"");

//Remove Button
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine mm=m.get(position);
                removeItem(mm);
            }
        });
    }

//Method to return the item which had choosed to remove
    private void removeItem(Medicine item)
    {
        int position=m.indexOf(item);
        m.remove(position);
        notifyItemRemoved(position);
    }

//This method to return the adapter array size
    @Override
    public int getItemCount() {
        return m.size();
    }

//The holder class
    class MedicineHolder extends RecyclerView.ViewHolder{
        ImageView iv ;
        TextView tv_N ,tv_T1 ,tv_ts,tv_T2,tv_T3;
        Button btn;
        public MedicineHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_addImage);
            tv_N=itemView.findViewById(R.id.tv_addName);
            tv_ts=itemView.findViewById(R.id.tv_addTimes);
            tv_T1=itemView.findViewById(R.id.tv_addtime1);
            btn=itemView.findViewById(R.id.remove_btn);
        }
    }
}