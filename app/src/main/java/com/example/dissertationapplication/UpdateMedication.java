package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateMedication extends AppCompatActivity {


    EditText medicationName, medicationDosage;
    DatePicker medicationDate;
    TimePicker medicationTime;

    String medID, medName, medDos, medDate, medTime;

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medication);

        medicationName = findViewById(R.id.medicationName1);
        medicationDosage = findViewById(R.id.medicationDosage1);
        medicationDate = (DatePicker) findViewById(R.id.datePicker1);
        medicationTime = (TimePicker) findViewById(R.id.timePicker2);

        medID = getIntent().getStringExtra("medicationID");
        medName = getIntent().getStringExtra("medicationName");
        medDos = getIntent().getStringExtra("medicationDosage");
        medDate = getIntent().getStringExtra("medicationDate");
        medTime = getIntent().getStringExtra("medicationTime");

        db = new DatabaseHelper(this);


        medicationName.setText(medName);
        medicationDosage.setText(medDos);


        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int userDate1 = medicationDate.getMonth()+1;
                String monthConverted = ""+userDate1;
                if(userDate1 <10){
                    monthConverted = "0"+monthConverted;
                }

                String updatedMedName = medicationName.getText().toString();
                String updatedMedDosage = medicationDosage.getText().toString();
                String updatedMedDate = medicationDate.getDayOfMonth() + "/" + monthConverted + "/" + medicationDate.getYear();
                String updatedMedTime = medicationTime.getCurrentHour() + " : " + medicationTime.getCurrentMinute();

                db.updateMedication(medID, updatedMedName, updatedMedDosage, updatedMedDate, updatedMedTime);
            }
        });

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean deleted = db.deleteMedication(medID);
                if (deleted == true){
                    Toast added = Toast.makeText(getApplicationContext(), "Medication Deleted! Please press the back arrow, to view!", Toast.LENGTH_LONG);
                    added.show();
                }
            }
        });
    }
}