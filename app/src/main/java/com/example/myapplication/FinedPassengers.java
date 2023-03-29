package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class FinedPassengers extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<FineModel> finelist;
    FineAdapter fineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fined_passengers);

        finelist = getIntent().getParcelableArrayListExtra("keyfinedlist");


        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(finelist.size()>0 && finelist!=null) {
            fineAdapter = new FineAdapter(FinedPassengers.this, finelist);
            recyclerView.setAdapter(fineAdapter);
        }

    }
}