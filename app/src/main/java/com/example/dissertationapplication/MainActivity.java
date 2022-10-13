package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Final Year Project"); //https://stackoverflow.com/questions/3438276/how-to-change-the-text-on-the-action-bar
        //Code used to change the title of the activity.
    }
    public void createAccount(View view) {
        Intent createAccountIntent = new Intent(this, CreateAccountClass.class);
        startActivity(createAccountIntent);
    }

    public void loginAccount(View view) {
        Intent loginAccount = new Intent(this, login.class);
        startActivity(loginAccount);
    }
}
