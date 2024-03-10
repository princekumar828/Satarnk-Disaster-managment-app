package com.bitlords.disasterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitlords.disasterapp.model.ReportModel;

import java.util.ArrayList;

public class MyAddapterCom extends RecyclerView.Adapter<MyAddapterCom.MyViewHolder> {
    Context context;
    ArrayList<ReportModel> list;

    public MyAddapterCom(Context context, ArrayList<ReportModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comunitycard,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       ReportModel mod=list.get(position);
       holder.title.setText(mod.getType());
       holder.about.setText(mod.getAbout());
       holder.location.setText(mod.getArea()+" " + mod.getPostCode());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView location,about,title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            about=itemView.findViewById(R.id.comSubtext);
            location=itemView.findViewById(R.id.Comlocation);
            title=itemView.findViewById(R.id.comTitile);

        }
    }
}
