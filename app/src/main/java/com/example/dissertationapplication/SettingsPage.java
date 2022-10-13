package com.example.dissertationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsPage extends AppCompatActivity {


    Button account, ff, customise, help, aboutUs, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        getSupportActionBar().setTitle("Final Year Project");

        account = findViewById(R.id.editAccount);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editDetails = new Intent(getApplicationContext(), editAccount.class);
                startActivity(editDetails);
            }
        });
        ff = findViewById(R.id.ffDetails);
        ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ffDetails = new Intent(getApplicationContext(), ffDetails.class);
                startActivity(ffDetails);
            }
        });
        customise = findViewById(R.id.Customise);
        customise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpPage = new Intent(getApplicationContext(), help.class);
                startActivity(helpPage);
            }
        });
        aboutUs = findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutUsPage = new Intent(getApplicationContext(), aboutus.class);
                startActivity(aboutUsPage);

            }
        });
        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOut = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logOut);
                finish();

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);
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
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext()
                                ,CalendarPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}