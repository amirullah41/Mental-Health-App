package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class AddCalendar extends AppCompatActivity {

    Button pickTime, pickDate;
    private DatePickerDialog datePickerDialog;
    int hour, minute, myIntValue;
    String chosenDate, chosenTime,newDay, newMonth;;
    EditText eventName, eventDesc;
    DatabaseHelper db;
    AlertDialog.Builder builder1;
    TextView test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);


        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("userID", -1);
        initDatePicker();
        pickDate = (Button) findViewById(R.id.pick_date_button);
        //pickDate.setText(getTodaysDate());
        pickTime = (Button) findViewById(R.id.pick_time_button);
        eventName = findViewById(R.id.event);
        eventDesc = findViewById(R.id.secondInput);
        builder1 = new AlertDialog.Builder(this);

        db = new DatabaseHelper(this);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                chosenDate = date;
                pickDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        if(day < 10){
            newDay = "0"+day;
        } else{
            newDay = String.valueOf(day);
        }

        if (month < 10){
            newMonth = "0"+month;
        } else{
            newMonth = String.valueOf(month);
        }
        return newDay + "/" + newMonth + "/" + year;
    }


    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void pickTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                pickTime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                chosenTime = String.format(Locale.getDefault(), "%02d:%02d",hour, minute);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void submitEvent(View view) {
        String newEventName = eventName.getText().toString();
        String newEventDesc = eventDesc.getText().toString();

        if(newEventDesc.equals("")||newEventDesc.equals("")||chosenDate.equals("") || chosenTime.equals("")){
            builder1.setMessage("Please fill in all fields!");
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
            if (db.insertEvent(newEventName, newEventDesc, chosenDate, chosenTime, myIntValue)) {
                Toast success = Toast.makeText(this, "Successfully added event! Press back arrow to view!", Toast.LENGTH_LONG);
                success.show();
            }else {
                Toast success = Toast.makeText(this, "Unsuccessfully added event! Press back arrow to view!", Toast.LENGTH_LONG);
                success.show();
            }
        }
    }
}