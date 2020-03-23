package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeScreen extends AppCompatActivity {
    TextView email_address_text, id_text;
    Button log_out, change_email, change_password;
    String myEmail, myID;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        log_out = findViewById(R.id.logout_button);
        change_email = findViewById(R.id.change_email_button_homescreen);
        change_password = findViewById(R.id.change_password_button_homescreen);
        email_address_text = findViewById(R.id.show_email_address);
        id_text = findViewById(R.id.show_ID);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                Toast.makeText(homeScreen.this, "Please log in to continue", Toast.LENGTH_SHORT).show();
            }
        });

        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locations = new Intent(homeScreen.this, changeEmail.class);
                startActivity(locations);
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locations = new Intent(homeScreen.this, changePassword.class);
                startActivity(locations);
            }
        });
    }

    
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(homeScreen.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out?");
        AlertDialog dialog = builder.create();
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                finish();
                Toast.makeText(homeScreen.this, "Please log in to continue", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser != null){
            myEmail = mUser.getEmail();
            myID = mUser.getUid();

            email_address_text.setText(myEmail);
            id_text.setText(myID);
        }
    }
}
