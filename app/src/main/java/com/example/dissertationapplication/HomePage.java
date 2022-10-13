package com.example.dissertationapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {


    ImageButton happy, neutral, sad;
    DatabaseHelper db;
    Button panicBtn, showToday;
    Integer myIntValue, happyCount, mediumCount, sadCount, count;
    String FFNumber, todayTasksString = "";
    ArrayList<String> todayTasks;
    TextView middleText;
    AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setTitle("Final Year Project");


        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("userID", -1);

        middleText = findViewById(R.id.dailyTasks);
        showToday = findViewById(R.id.showTodayTask);
        builder1 = new AlertDialog.Builder(this);
        happyCount = 0;
        mediumCount = 0;
        sadCount = 0;
        count = 0;
        db = new DatabaseHelper(this);

        if (db.checkIfMoods(myIntValue) == false){
            db.insertMoods(happyCount, mediumCount, sadCount, myIntValue);
        }
        showToday.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                showTodayTasks();
            }
        });

        panicBtn = findViewById(R.id.panicbtn);
        panicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = String.valueOf(myIntValue);
                FFNumber = db.getFFNumber(ID);
                callPhoneNumber();
            }
        });
        happy = (ImageButton) findViewById(R.id.happy);
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happyCount += 1;
                db.updateHappyCount(happyCount, myIntValue);
                builder1.setMessage("Glad you're happy! Keep it up!");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Okay :)",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        neutral = (ImageButton) findViewById(R.id.neutral);
        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediumCount += 1;
                db.updateMidCount(mediumCount, myIntValue);
                builder1.setMessage("Keep working! Today can only get better!");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Okay :)",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        sad = (ImageButton) findViewById(R.id.dislike);
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadCount += 1;
                db.updateSadCount(sadCount, myIntValue);
                builder1.setMessage("Don't worry if today's been tough! Lets get back on track and make it better!!");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Okay :)",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        BottomNavigationView tabBar = findViewById(R.id.tabBar);
        tabBar.setSelectedItemId(R.id.home);
        tabBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.medication:
                        Intent medicationIntent = new Intent(getApplicationContext(), MedicationPage.class);
                        startActivity(medicationIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext()
                                ,CalendarPage.class));
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showTodayTasks(){
        todayTasks = db.getTasksForToday();

        Toast.makeText(this, ">>>>>>>>", Toast.LENGTH_SHORT).show();
        for (int i=0; i<todayTasks.size(); i++){
            if (todayTasksString.contains(todayTasks.get(i))){
                Toast.makeText(this, "already added", Toast.LENGTH_SHORT).show();
            }else{
                todayTasksString += todayTasks.get(i);
            }
        }

        if (!todayTasksString.contains(todayTasksString)){
            Toast.makeText(this, "Shown", Toast.LENGTH_SHORT).show();
        }else{
            middleText.setText(todayTasksString);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
        }
    }
    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomePage.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + FFNumber));
                startActivity(callIntent);
            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + FFNumber));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Please add an Friend of Family in the Settings page!", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}
