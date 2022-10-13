package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class editAccount extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<String> returnedItems;
    EditText name, emailChange, pass, confPass;
    AlertDialog.Builder builder1;
    int myIntValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        db = new DatabaseHelper(this);

        name = findViewById(R.id.Fullname);
        emailChange = findViewById(R.id.emailChange);
        pass = findViewById(R.id.updatedPass);
        confPass = findViewById(R.id.ConfirmNewPass);
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
         myIntValue = sp.getInt("userID", -1);
        returnedItems = new ArrayList<String>();
        builder1 = new AlertDialog.Builder(this);

        returnedItems = db.getLoggedInUserEmailPass(myIntValue);
        String fullName = returnedItems.get(0).toString();
        String email = returnedItems.get(1).toString();
        String password = returnedItems.get(2).toString();
        Toast.makeText(this, ""+fullName, Toast.LENGTH_SHORT).show();

        name.setText(fullName);
        emailChange.setText(email);
        pass.setText(password);
        confPass.setText(password);
    }

    public void updateAccountDetails(View view) {
        String newFullName = name.getText().toString();
        String newEmail = emailChange.getText().toString();
        String newPassword = pass.getText().toString();
        String confNewPassword = confPass.getText().toString();

        String newID = String.valueOf(myIntValue);

        if(newFullName.equals("")||newEmail.equals("")||newPassword.equals("")||confNewPassword.equals("")) {
            builder1.setMessage("Please fill in all fields :)");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Ok!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }else{
            if (newPassword.equals(confNewPassword)){
                if ( !newPassword.equals(newPassword.toLowerCase()) && !newPassword.equals(newPassword.toUpperCase()) && newPassword.matches(".*\\d+.*") && newPassword.length() >=8){
                    if(db.updateUserInformation(newID, newFullName, newEmail, newPassword)){
                        Toast.makeText(this, "Worked", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Didn't Work", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    builder1.setMessage("Password must contain a Capital Letter and a special Character, and be 8 or more characters long.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Ok!",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }else{
                builder1.setMessage("Password's Don't match!");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ok!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }
    }
}