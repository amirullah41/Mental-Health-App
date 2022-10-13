package com.example.dissertationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MedicationPage extends AppCompatActivity {
    FloatingActionButton addButton;

    DatabaseHelper db;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ArrayList<String> medicationID, medicationName, medicationDosage, medicationTime, medicationDate;
    int myIntValue;
    AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_page);
        getSupportActionBar().setTitle("Final Year Project");

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("userID", -1);

        medicationID = new ArrayList<>();
        medicationName = new ArrayList<>();
        medicationDosage = new ArrayList<>();
        medicationTime = new ArrayList<>();
        medicationDate = new ArrayList<>();

        builder1 = new AlertDialog.Builder(this);

        addButton = (FloatingActionButton) findViewById(R.id.add_button);
        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMed = new Intent(MedicationPage.this, AddMedication.class);
                startActivityForResult(addMed, 2);
            }
        });

        storeDataInArrays();
        customAdapter = new CustomAdapter(MedicationPage.this, this, medicationID, medicationName, medicationDosage, medicationTime, medicationDate);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.medication);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext()
                                ,CalendarPage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomePage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.memories:
                        startActivity(new Intent(getApplicationContext()
                                ,MemoriesPage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext()
                                ,SettingsPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 2) {
            recreate();
        }
    }

    public void storeDataInArrays(){
        Cursor cursor = db.readAllData(myIntValue);
        if(cursor.getCount() == 0){
            builder1.setMessage("Currently you have no medication! Please add some :)");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }else{
            while (cursor.moveToNext()){
                medicationID.add(cursor.getString(0));
                medicationName.add(cursor.getString(1));
                medicationDosage.add(cursor.getString(2));
                medicationDate.add(cursor.getString(3));
                medicationTime.add(cursor.getString(4));
            }
        }
    }
}