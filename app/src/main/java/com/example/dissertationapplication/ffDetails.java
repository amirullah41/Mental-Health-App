package com.example.dissertationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ffDetails extends AppCompatActivity {

    EditText fullName, email, phoneNumber, relationship;
    DatabaseHelper db;
    int myIntValue;
    AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ff_details);

        fullName = findViewById(R.id.Ff_fullName);
        email = findViewById(R.id.ff_email);
        phoneNumber = findViewById(R.id.ff_phonenumber);
        relationship = findViewById(R.id.ff_relationship);
        builder1 = new AlertDialog.Builder(this);

        db = new DatabaseHelper(this);
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("userID", -1);
    }

    public void addFF(View view) {
        String Ff_fullName = fullName.getText().toString();
        String ff_email = email.getText().toString();
        String ff_phoneNumber = phoneNumber.getText().toString();
        String ff_relationship = relationship.getText().toString();
        if(Ff_fullName.equals("")||ff_email.equals("")||ff_phoneNumber.equals("")||ff_relationship.equals("")){
            builder1.setMessage("Please fill in all fields :)");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Ok!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            db.deleteFF(myIntValue);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }else {
            if(db.checkFFEntered(myIntValue)){
                builder1.setMessage("You can only have one Friend or Family entered per Account. Would you like to remove this one?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteFF(myIntValue);
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
            }else{
                if (db.insertFFData(Ff_fullName, ff_email, ff_phoneNumber, ff_relationship, myIntValue)){
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}