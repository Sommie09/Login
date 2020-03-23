package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {
    EditText register_email, register_password, register_repeat_password;
    Button register;
    ValidateInput validateInput;

    private FirebaseAuth mAuth;

    LoadingAnimation loadingAnimation;



    String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_email = findViewById(R.id.email_address_register);
        register_password = findViewById(R.id.password_register);
        register_repeat_password = findViewById(R.id.repeat_password_register);
        register = findViewById(R.id.register_button_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loadingAnimation = new LoadingAnimation(registerActivity.this);

        validateInput = new ValidateInput(registerActivity.this, register_email, register_password, register_repeat_password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewAccount();
            }
        });
    }



    public void backArrowRegister(View view) {
        Intent locations = new Intent(this, loginActivity.class);
        startActivity(locations);
    }


    public void registerNewAccount(){



        boolean emailVerified = validateInput.ValidateEmail();
        boolean passWordVerified = validateInput.ValidatePassword();
        boolean repeatPassWordVerified = validateInput.repeatPasswordValidation();

        if(emailVerified && passWordVerified && repeatPassWordVerified) {

            email = register_email.getText().toString().trim();
            password = register_password.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(registerActivity.this, homeScreen.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(registerActivity.this, "Fatal Error", Toast.LENGTH_SHORT).show();
                                loadingAnimation.dismissLoadingAnimation();
                            }

                            // ...
                        }
                    });
              }else{
            loadingAnimation.dismissLoadingAnimation();

        }



    }


}
