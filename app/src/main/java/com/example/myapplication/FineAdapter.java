package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FineAdapter extends RecyclerView.Adapter<FineAdapter.FineViewHolder> {

    Context context;
    ArrayList<FineModel> finelist;

    public FineAdapter(Context context, ArrayList<FineModel> finelist) {
        this.context = context;
        this.finelist = finelist;
    }

    @NonNull
    @Override
    public FineAdapter.FineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.fine_item,parent,false);
        return new FineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FineAdapter.FineViewHolder holder, int position) {
        FineModel fine = finelist.get(position);
        holder.name.setText(fine.name);
        holder.reason.setText(fine.reason);
        holder.amount.setText(String.valueOf(fine.amount));
    }

    @Override
    public int getItemCount() {
        return finelist.size();
    }

    public static class FineViewHolder extends RecyclerView.ViewHolder{

        TextView name, reason, amount;


        public FineViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            reason = itemView.findViewById(R.id.tv_reason);
            amount = itemView.findViewById(R.id.tv_amount);
        }
    }
}
