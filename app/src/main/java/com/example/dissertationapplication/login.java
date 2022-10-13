package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText emailAd, password;
    Button btnlogin;
    DatabaseHelper DB;
    String email, pass;
    Boolean checkUserPassword;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Final Year Project");
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        editor = sp.edit();
        emailAd = (EditText) findViewById(R.id.emailChange);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DatabaseHelper(this);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });
    }

    public void checkDetails(){
        email = emailAd.getText().toString();
        pass = password.getText().toString();

        if(email.equals("")||pass.equals(""))
            Toast.makeText(login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else{
            checkUserPassword = DB.checkDetails(email, pass);
            if(checkUserPassword==true){
                Toast.makeText(login.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getApplicationContext(), HomePage.class);
                Integer ID = DB.getLoggedInUserID(email, pass);
                editor.putInt("userID", ID);
                Toast.makeText(login.this, ""+ID, Toast.LENGTH_SHORT).show();
                editor.commit();
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }
}