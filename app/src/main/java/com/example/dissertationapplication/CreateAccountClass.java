package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountClass extends AppCompatActivity {
    EditText username, password, rePassword, email;
    String user, pass, repass, emailAddress;
    TextView welcome;
    Button signUp, signIn;
    DatabaseHelper DB;
    AlertDialog.Builder builder1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_class);

        getSupportActionBar().setTitle("Final Year Project");

        username = (EditText) findViewById(R.id.Fullname);
        password = (EditText) findViewById(R.id.updatedPass);
        rePassword = (EditText) findViewById(R.id.ConfirmNewPass);
        email = (EditText) findViewById(R.id.emailChange);
        welcome = (TextView) findViewById(R.id.textView);
        welcome.setText("Welcome! Please create an account below.");
        signUp = (Button) findViewById(R.id.btnsignup);
        signIn = (Button) findViewById(R.id.btnsignin);
        builder1 = new AlertDialog.Builder(this);
        DB = new DatabaseHelper(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 addNewAccount();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInToAccount();
            }
        });
    }

    public void addNewAccount(){
        user = username.getText().toString();
        pass = password.getText().toString();
        repass = rePassword.getText().toString();
        emailAddress = email.getText().toString();

        if(user.equals("")||pass.equals("")||repass.equals("")||email.equals(""))
            Toast.makeText(CreateAccountClass.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else{
            if( !pass.equals(pass.toLowerCase()) && !pass.equals(pass.toUpperCase()) && pass.matches(".*\\d+.*") && pass.length() >8){
                if(pass.equals(repass)){
                    Boolean checkEmail = DB.checkEmail(emailAddress);
                    if(checkEmail == false){
                        Boolean insert = DB.insertData(user, pass, emailAddress);
                        if(insert==true){
                            Toast.makeText(CreateAccountClass.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(CreateAccountClass.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(CreateAccountClass.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CreateAccountClass.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }
            }else{
                builder1.setMessage("Password must contain at least 1 Uppercase Letter and 1 Special Character");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                Toast.makeText(this, "Password doesn't contain a Capital letter or Special Character!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signInToAccount() {
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
    }


}