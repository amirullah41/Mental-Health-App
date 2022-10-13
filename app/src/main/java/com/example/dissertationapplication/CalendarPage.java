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
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarPage extends AppCompatActivity {


    CalendarView calendar;
    DatabaseHelper db;
    TextView taskDisplay;
    RelativeLayout relativeLayoutForCalendar;
    RecyclerView recyclerView;
    CustomAdapterCalendarView customAdapterCalendar;
    int day, month, year, myIntValue;
    ArrayList<String> taskID, taskTitle, taskDesc, taskDate, taskTime;
    AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        getSupportActionBar().setTitle("Final Year Project");

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("userID", -1);


        recyclerView = findViewById(R.id.recyclerView2);

        taskID = new ArrayList<>();
        taskTitle = new ArrayList<>();
        taskDesc = new ArrayList<>();
        taskDate = new ArrayList<>();
        taskTime = new ArrayList<>();
        builder1 = new AlertDialog.Builder(this);


        db = new DatabaseHelper(this);

        storeDataInArrays();
        customAdapterCalendar = new CustomAdapterCalendarView(CalendarPage.this, this, taskID, taskTitle, taskDesc, taskDate, taskTime);
        recyclerView.setAdapter(customAdapterCalendar);
        recyclerView.setLayoutManager(new LinearLayoutManager((CalendarPage.this)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.calendar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.medication:
                        startActivity(new Intent(getApplicationContext()
                                ,MedicationPage.class));
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

        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEvent = new Intent(getApplicationContext(), AddCalendar.class);
                startActivity(addEvent);
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

    void storeDataInArrays(){
        Cursor cursor = db.readAllTasks(myIntValue);
        if(cursor.getCount() == 0){
            builder1.setMessage("Currently you have no tasks loaded! Why don't you add some? :)");
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
                taskID.add(cursor.getString(0));
                taskTitle.add(cursor.getString(1));
                taskDesc.add(cursor.getString(2));
                taskDate.add(cursor.getString(3));
                taskTime.add(cursor.getString(4));
            }
        }
    }
}