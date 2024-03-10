package com.bitlords.disasterapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitlords.disasterapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FundAddapter extends RecyclerView.Adapter<FundAddapter.MyViewHolder> {
    Context context;
    ArrayList<FundRaiserModel> list;

    public FundAddapter(Context context, ArrayList<FundRaiserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FundAddapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fund_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FundAddapter.MyViewHolder holder, int position) {
        FundRaiserModel model=list.get(position);
        holder.title.setText(model.getTitle());
        holder.about.setText(model.getSubTitle());
        holder.totalFund.setText("Total Amount:"+model.getTotAmo());
        holder.leftFund.setText("Recoverd Amount:"+model.getRevAmo());

        Picasso.with(context)
                .load(model.getImgUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,about,totalFund,leftFund;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.funTitile);
            totalFund=itemView.findViewById(R.id.funtotal);
            leftFund=itemView.findViewById(R.id.funReco);
            about=itemView.findViewById(R.id.funSubText);
            imageView=itemView.findViewById(R.id.funImg);

        }
    }
}
