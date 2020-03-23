package com.example.login;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

class ValidateInput {

    private Context context;
    private EditText email, password, repeatPassword;

    String emailInput, passWordInput, repeatPasswordInput;

    ValidateInput(Context myContext, EditText myEmail){
        context = myContext;
        email = myEmail;
    }


    ValidateInput(Context myContext, EditText myEmail, EditText myPassword){
        context = myContext;
        email = myEmail;
        password = myPassword;
    }

    public ValidateInput(Context myContext, EditText myEmail, EditText myPassword, EditText myRepeatPassword) {
        context = myContext;
        email = myEmail;
        password = myPassword;
        repeatPassword = myRepeatPassword;
    }

    boolean ValidateEmail() {
        emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            Toast.makeText(context, "Please enter your Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(context, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    boolean ValidatePassword() {
        passWordInput = password.getText().toString().trim();

        if (passWordInput.isEmpty()) {
            Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passWordInput.length() < 8) {
            Toast.makeText(context, "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    boolean repeatPasswordValidation() {
        repeatPasswordInput = repeatPassword.getText().toString().trim();


        if(repeatPasswordInput.isEmpty()){
            Toast.makeText(context, "Fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!repeatPasswordInput.equals(passWordInput)){
            Toast.makeText(context, "Passwords Don't match.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }
}
