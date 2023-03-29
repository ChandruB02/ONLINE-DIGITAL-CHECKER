package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class UncheckedPassengers extends AppCompatActivity {

    RecyclerView recyclerView;
    ReportAdapter reportAdapter;
    ArrayList<PassengerModel> uncheckedlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unchecked_passengers);

        uncheckedlist = getIntent().getParcelableArrayListExtra("keyuncheckedlist");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(uncheckedlist.size()>0 && uncheckedlist!=null) {
            reportAdapter = new ReportAdapter(UncheckedPassengers.this, uncheckedlist);
            recyclerView.setAdapter(reportAdapter);
        }
    }
}