package com.example.ankurshukla.hostel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ankur Shukla on 11/3/2015.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewholder> {

    ArrayList<String> data = new ArrayList<>();
    private LayoutInflater inflater;

    public Adapter(Context context,ArrayList<String> data){
       inflater = LayoutInflater.from(context);
        this.data = data;
   }


    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row,parent,false);
        MyViewholder holder = new MyViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
       String current = data.get(position);
        holder.text.setText(current);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder{

        TextView text;
        public MyViewholder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.custom_text);
        }

    }
}
