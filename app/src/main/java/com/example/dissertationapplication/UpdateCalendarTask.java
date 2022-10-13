package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class UpdateCalendarTask extends AppCompatActivity {

    Button pickTime, pickDate;
    private DatePickerDialog datePickerDialog;
    int hour, minute;
    String chosenDate, chosenTime;
    EditText eventName, eventDesc;
    String taskID, taskTitle, taskDesc, taskDate, taskTime;
    DatabaseHelper db;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_calendar_task);
        initDatePicker();
        pickDate = (Button) findViewById(R.id.pick_date_button1);
        pickTime = (Button) findViewById(R.id.pick_time_button1);
        eventName = findViewById(R.id.event1);
        eventDesc = findViewById(R.id.secondInput1);
        db = new DatabaseHelper(this);

        taskID = getIntent().getStringExtra("taskID");
        taskTitle = getIntent().getStringExtra("taskName");
        taskDesc = getIntent().getStringExtra("taskDesc");
        taskDate = getIntent().getStringExtra("taskDate");
        taskTime = getIntent().getStringExtra("taskTime");

        eventName.setText(taskTitle);
        eventDesc.setText(taskDesc);

        Button updateButton = findViewById(R.id.updateEvent);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTaskName = eventName.getText().toString();
                String newTaskDesc = eventDesc.getText().toString();
                db.updateTask(taskID, newTaskName, newTaskDesc, chosenDate, chosenTime);
            }
        });

        Button deleteButton = findViewById(R.id.deleteEvent);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.deleteTask(taskID)){
                    Toast.makeText(getApplicationContext(), "Delete Successfully", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Delete Unsuccessful", Toast.LENGTH_LONG).show();

                }
            }
        });
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
        return String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
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

    public void updateEvent(View view) {
        String newTaskName = eventName.getText().toString();
        String newTaskDesc = eventDesc.getText().toString();
        db.updateTask(taskID, newTaskName, newTaskDesc, chosenDate, chosenTime);
    }
}