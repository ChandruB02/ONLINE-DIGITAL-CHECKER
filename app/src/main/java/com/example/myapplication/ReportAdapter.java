package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    Context context;
    ArrayList<PassengerModel> list;

    public ReportAdapter(Context context, ArrayList<PassengerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.report_item,parent,false);
        return new ReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        PassengerModel passengerModel = list.get(position);

        holder.seatno.setText(String.valueOf(passengerModel.Seatno));
        holder.name.setText(passengerModel.Name);
        holder.source.setText(passengerModel.Source);
        holder.dest.setText(passengerModel.Destination);
        holder.id.setText(passengerModel.ID);
        holder.status.setText(passengerModel.Status);

        if(holder.status.getText().equals("Checked"))
            holder.cardView.setCardBackgroundColor(Color.parseColor("#916DD1"));
        if(holder.status.getText().equals("Unchecked"))
            holder.cardView.setCardBackgroundColor(Color.parseColor("#BA9832"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder{

        TextView seatno,name,source,dest,id,status;
        CardView cardView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            seatno = itemView.findViewById(R.id.tv_seatno);
            name = itemView.findViewById(R.id.tv_name);
            source = itemView.findViewById(R.id.tv_source);
            dest = itemView.findViewById(R.id.tv_destination);
            id = itemView.findViewById(R.id.tv_id);
            status = itemView.findViewById(R.id.tv_status);

            cardView = itemView.findViewById(R.id.passenger_card);
        }
    }
}
