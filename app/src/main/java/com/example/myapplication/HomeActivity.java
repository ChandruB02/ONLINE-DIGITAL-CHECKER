package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public TextView tv,tv_name;
    public Button button,logout;
    public Spinner spinner,spinner1;
    final Calendar myCalendar= Calendar.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;

    String selecteddate, selectedtrainno, selectedtrainname, selectedcoach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

        tv = (TextView) findViewById(R.id.textView6);
        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner1 = (Spinner) findViewById(R.id.spinner3);
        tv_name = (TextView) findViewById(R.id.textView5);
        button = (Button) findViewById(R.id.button);
        logout = (Button) findViewById(R.id.button4);

        CollectionReference trains = db.collection("trains");
        List<String> train = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_selected_item,train);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        trains.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        String singletrain = document.getString("Train_no");
                        train.add(singletrain);
                    }
                    adapter.notifyDataSetChanged();

                }
                else {
                    Exception e = task.getException();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,PassengersActivity.class);
                intent.putExtra("keytrainno",selectedtrainno);
                intent.putExtra("keycoach",selectedcoach);
                intent.putExtra("keydate",selecteddate);
                startActivity(intent);
            }
        });

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }

        };

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(HomeActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                DatePicker dp = dpd.getDatePicker();
                dp.setMinDate(System.currentTimeMillis() - 1000);
                dpd.show();

            }
        });
    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        selecteddate = dateFormat.format(myCalendar.getTime());
        tv.setText(selecteddate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinner2){
            selectedtrainno = parent.getSelectedItem().toString();
            DocumentReference trains = db.collection("trains").document(selectedtrainno);
            trains.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                    if (task1.isSuccessful()) {
                        DocumentSnapshot document = task1.getResult();
                        if (document.exists()) {
                            selectedtrainname = document.getString("Name");
                            tv_name.setText(selectedtrainname);
                        } else {
                            Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Exception e = task1.getException();
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });


            CollectionReference coaches = db.collection("trains").document(selectedtrainno).collection("coaches");
            List<String> coach = new ArrayList<>();
            ArrayAdapter<String> coachadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_selected_item,coach);
            coachadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(coachadapter);
            coaches.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            String singlecoach = document.getString("Name");
                            coach.add(singlecoach);
                        }
                        coachadapter.notifyDataSetChanged();

                        if(progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                    else {
                        Exception e = task.getException();
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        if(parent.getId() == R.id.spinner3) {
            selectedcoach = parent.getSelectedItem().toString();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}