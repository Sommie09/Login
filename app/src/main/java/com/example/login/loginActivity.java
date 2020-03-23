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
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    Button login;
    EditText login_email, login_password;
    ValidateInput validateInput;
    private FirebaseAuth mAuth;

    String email, password;

    LoadingAnimation loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        login_email = findViewById(R.id.email_address_login);
        login_password = findViewById(R.id.password_login);
        login = findViewById(R.id.login_button);

        //Loading Animation
        loadingAnimation = new LoadingAnimation(loginActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginAccount();
            }
        });

        validateInput = new ValidateInput(loginActivity.this, login_email, login_password);

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            Intent locations = new Intent(loginActivity.this, homeScreen.class);
            startActivity(locations);
        } else {
            Toast.makeText(loginActivity.this, "Please log in to continue", Toast.LENGTH_SHORT).show();
        }
    }


    public void backArrow(View view) {
        Intent locations = new Intent(this, MainActivity.class);
        startActivity(locations);
    }


    public void loginAccount() {

        loadingAnimation.LoadingAnimationDialog();

        boolean emailVerified = validateInput.ValidateEmail();
        boolean passWord = validateInput.ValidatePassword();

        if (emailVerified && passWord) {

            email = login_email.getText().toString().trim();
            password = login_password.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(loginActivity.this, homeScreen.class);
                                startActivity(intent);
                                loadingAnimation.dismissLoadingAnimation();
                            } else {
                                Toast.makeText(loginActivity.this, "Fatal Error", Toast.LENGTH_SHORT).show();
                                loadingAnimation.dismissLoadingAnimation();
                            }
                        }
                    });
        } else {
            loadingAnimation.dismissLoadingAnimation();


        }


    }
}
