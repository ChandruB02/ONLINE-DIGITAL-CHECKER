package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CheckedPassengers extends AppCompatActivity {
    RecyclerView recyclerView;
    ReportAdapter reportAdapter;
    ArrayList<PassengerModel> checkedlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_passengers);

        checkedlist = getIntent().getParcelableArrayListExtra("keycheckedlist");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(checkedlist.size()>0 && checkedlist!=null) {
            reportAdapter = new ReportAdapter(CheckedPassengers.this, checkedlist);
            recyclerView.setAdapter(reportAdapter);
        }


    }
}