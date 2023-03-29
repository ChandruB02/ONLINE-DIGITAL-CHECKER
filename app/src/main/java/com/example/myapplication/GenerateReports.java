package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GenerateReports extends AppCompatActivity {

    String status;
    CardView cardView1,cardView2,cardView3;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_reports);

        Intent intent = getIntent();
        ArrayList<PassengerModel> passengerlist = intent.getParcelableArrayListExtra("keypassengerlist");
        ArrayList<FineModel> finelist = intent.getParcelableArrayListExtra("keyfinelist");

        ArrayList<PassengerModel> checkedlist = new ArrayList<>();
        ArrayList<PassengerModel> uncheckedlist = new ArrayList<>();

        for(PassengerModel item: passengerlist){
            status = item.getStatus().toString();
            if(status.equals("Unchecked"))
                uncheckedlist.add(item);
            if(status.equals("Checked"))
                checkedlist.add(item);
        }

        System.out.println(checkedlist);
        System.out.println(uncheckedlist);

        cardView1 = (CardView) findViewById(R.id.cardView1);
        cardView2 = (CardView) findViewById(R.id.cardView2);
        cardView3 = (CardView) findViewById(R.id.cardView3);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GenerateReports.this,CheckedPassengers.class);
                intent1.putExtra("keycheckedlist",checkedlist);
                startActivity(intent1);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GenerateReports.this,UncheckedPassengers.class);
                intent2.putExtra("keyuncheckedlist",uncheckedlist);
                startActivity(intent2);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(GenerateReports.this,FinedPassengers.class);
                intent3.putExtra("keyfinedlist",finelist);
                startActivity(intent3);
            }
        });

        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GenerateReports.this);

                builder.setMessage("Are you sure you are done")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GenerateReports.this,HomeActivity.class);
                                startActivity(intent);
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
}