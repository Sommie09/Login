package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changeEmail extends AppCompatActivity {

    EditText current_email, new_email;
    Button changeEmailButton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ValidateInput validateInput;

    TextView send_verification_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        current_email = findViewById(R.id.current_email_edittext);
        new_email = findViewById(R.id.new_email_edittext);
        changeEmailButton = findViewById(R.id.change_email_button);
        send_verification_email = findViewById(R.id.send_verification_email);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        validateInput = new ValidateInput(changeEmail.this, new_email);

        setCurrentEmail();

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean emailVerified = validateInput.ValidateEmail();

                if (emailVerified && mUser != null) {
                    String myNewEmail = new_email.getText().toString().trim();
                    mUser.updateEmail(myNewEmail);
                    Toast.makeText(changeEmail.this, "Email Address Updated Successfully", Toast.LENGTH_SHORT).show();
                    //Wait 1 sec to read email from firebase then update edit text
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentEmail();
                        }
                    }, 2000);
                } else {
                    Toast.makeText(changeEmail.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                }

            }
        });

        send_verification_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.sendEmailVerification();
                Toast.makeText(changeEmail.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
            }
        });


    }




    public void setCurrentEmail(){
        if(mUser != null){
            current_email.setEnabled(true);
            current_email.setText(mUser.getEmail());
            current_email.setEnabled(false);
        }else{
            Toast.makeText(changeEmail.this, "Please log in to continue", Toast.LENGTH_SHORT).show();
        }
    }


}
