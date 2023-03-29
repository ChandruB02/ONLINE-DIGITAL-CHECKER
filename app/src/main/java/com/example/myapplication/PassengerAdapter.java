package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder> {

    Context context;
    ArrayList<PassengerModel> passengerlist;
    ArrayList<PassengerModel> checkedlist;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){mlistener=listener;}

    public PassengerAdapter(Context context, ArrayList<PassengerModel> passengerlist) {
        this.context = context;
        this.passengerlist = passengerlist;
    }


    @NonNull
    @Override
    public PassengerAdapter.PassengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.passenger_item,parent,false);
        return new PassengerViewHolder(v,mlistener);
    }

    public int index=-1;

    @Override
    public void onBindViewHolder(@NonNull PassengerAdapter.PassengerViewHolder holder,int position) {
        final PassengerModel passengerModel = passengerlist.get(position);

        holder.seatno.setText(String.valueOf(passengerModel.Seatno));
        holder.name.setText(passengerModel.Name);
        holder.source.setText(passengerModel.Source);
        holder.dest.setText(passengerModel.Destination);
        holder.id.setText(passengerModel.ID);
        holder.status.setText(passengerModel.Status);

        if(holder.status.getText().toString().equals("Unchecked"))
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F64747"));
        else if(holder.status.getText().toString().equals("Checked"))
            holder.cardView.setCardBackgroundColor(Color.parseColor("#648043"));

    }

    @Override
    public int getItemCount() {
        return passengerlist.size();
    }

    public static class PassengerViewHolder extends RecyclerView.ViewHolder{

        TextView seatno,name,source,dest,id,status;
        Button button;
        CardView cardView;

        public PassengerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            seatno = itemView.findViewById(R.id.tv_seatno);
            name = itemView.findViewById(R.id.tv_name);
            source = itemView.findViewById(R.id.tv_source);
            dest = itemView.findViewById(R.id.tv_destination);
            id = itemView.findViewById(R.id.tv_id);
            status = itemView.findViewById(R.id.tv_status);

            button = itemView.findViewById(R.id.button9);
            cardView = itemView.findViewById(R.id.passenger_card);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                            if(status.getText().toString().equals("Unchecked"))
                                cardView.setCardBackgroundColor(Color.parseColor("#F64747"));
                            else if(status.getText().toString().equals("Checked"))
                                cardView.setCardBackgroundColor(Color.parseColor("#648043"));
                        }
                    }
                }
            });


        }
    }

    public  void filterList(ArrayList<PassengerModel> filteredList){
        passengerlist = filteredList;
        notifyDataSetChanged();
    }
}
