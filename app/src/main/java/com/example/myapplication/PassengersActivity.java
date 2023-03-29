package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PassengersActivity extends AppCompatActivity implements CustomDialog.CustomDialogListener {

    TextView train,coachno,date;
    RecyclerView rv_passengers;
    ArrayList<PassengerModel> passengerlist,checkedlist;
    PassengerAdapter passengerAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    EditText search;
    Button generate,fine;
    FineModel data;
    ArrayList<FineModel> finelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers);

        finelist = new ArrayList<FineModel>();

        fine = (Button)findViewById(R.id.button6);
        fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        generate = (Button)findViewById(R.id.button7);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PassengersActivity.this);

                builder.setMessage("Are you sure you want to proceed")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PassengersActivity.this,GenerateReports.class);
                                intent.putExtra("keypassengerlist",passengerlist);
                                intent.putExtra("keyfinelist",finelist);
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

        search = findViewById(R.id.et_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

        train = (TextView) findViewById(R.id.textView8);
        coachno = (TextView) findViewById(R.id.textView9);
        date = (TextView) findViewById(R.id.textView10);

        String trainno = getIntent().getStringExtra("keytrainno");
        String coach = getIntent().getStringExtra("keycoach");
        String traveldate = getIntent().getStringExtra("keydate");

        train.setText("Train: " + trainno);
        coachno.setText("Coach: " + coach);
        date.setText(traveldate);

        rv_passengers = (RecyclerView) findViewById(R.id.rv_passengers);
        rv_passengers.setHasFixedSize(true);
        rv_passengers.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        passengerlist = new ArrayList<PassengerModel>();
        checkedlist = new ArrayList<PassengerModel>();
        passengerAdapter = new PassengerAdapter(PassengersActivity.this, passengerlist);

        rv_passengers.setAdapter(passengerAdapter);
        passengerAdapter.setOnItemClickListener(new PassengerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                PassengerModel data = passengerlist.get(position);
                if(data.getStatus().equals("Unchecked")) {
                    data.setStatus("Checked");
                }
                else if(data.getStatus().equals("Checked"))
                    data.setStatus("Unchecked");
                passengerAdapter.notifyItemChanged(position);
            }
        });

        EventChangeListner();

    }

    private void filter(String text){
        ArrayList<PassengerModel> filteredList = new ArrayList<>();

        for(PassengerModel item: passengerlist){
            if(item.getSource().toLowerCase().startsWith(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        passengerAdapter.filterList(filteredList);
    }

    public void openDialog(){
        CustomDialog dialog = new CustomDialog();
        dialog.show(getSupportFragmentManager(),"custom dialog");
    }

    private void EventChangeListner() {
            db.collection("passengers").orderBy("Seatno", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("firestore error",error.getMessage());
                    return;
                }

                for(DocumentChange dc: value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        passengerlist.add(dc.getDocument().toObject(PassengerModel.class));
                    }

                    passengerAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }

            }
        });
    }

    @Override
    public void collectdata(String name, String reason, long amount) {
        Toast.makeText(getApplicationContext(),"Fine collected",Toast.LENGTH_LONG).show();
        data = new FineModel(name,reason,amount);
        finelist.add(data);
    }
}