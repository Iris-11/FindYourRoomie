package com.task.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.regex.*;


public class Signup extends AppCompatActivity {
    EditText username,email,password,confirmPassword;
    Button signupBtn,backBtn;
    TextView error;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        error = findViewById(R.id.errorInfo);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById((R.id.confirmPassword));
        signupBtn = findViewById(R.id.signupBtn);
        backBtn=findViewById(R.id.button);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strUsername = username.getText().toString();
                String strEmail = email.getText().toString();
                String strPass = password.getText().toString();
                String strConfirmPass = confirmPassword.getText().toString();

                if(strUsername.isEmpty() || strEmail.isEmpty() || strPass.isEmpty() || strConfirmPass.isEmpty()){
                    error.setText("All fields required!");
                    error.setTextColor(Color.parseColor("#F44336"));
                    return;
                }
                else {
                    //password validation


                    if (!(isValidPassword(strPass))) {
                        error.setText("Passwords must be 8-20 characters long, contain at least one digit, one uppercase and lowercase letter, one special character (!@#$%^&+=), and no spaces.");
                        error.setTextColor(Color.parseColor("#F44336"));
                        return;
                    }

                    else {
                        if (!strPass.equals(strConfirmPass)) {
                            error.setText("Passwords must match!");
                            error.setTextColor(Color.parseColor("#F44336"));
                            return;
                        }
                        else{
                            DbConnect conn = new DbConnect(getApplicationContext());
                            if(conn.userExists(strUsername)){
                                error.setText("This username already exists!");
                                error.setTextColor(Color.parseColor("#F44336"));
                                return;
                            }
                            else {
                                if (conn.emailExists(strEmail)) {
                                    error.setText("This email already has an account!");
                                    error.setTextColor(Color.parseColor("#F44336"));
                                    return;
                                } else {
                                    try {
                                        User newUser = new User(strUsername, strEmail, strPass.getBytes(StandardCharsets.UTF_8));
                                        conn.addUser(newUser);
                                        Toast.makeText(Signup.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username", username.getText().toString());
                                        //   editor.putBoolean("isLoggedIn", true);
                                        editor.apply(); // Commit changes
                                        // Start the Welcome activity
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        i.putExtra("email",strEmail);


                                        startActivity(i);
                                        finish(); // Close current activity

                                    } catch (Exception e) {
                                        error.setText("Something went wrong!:"+e.getMessage());
                                        error.setTextColor(Color.parseColor("#F44336"));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Signup.this,Welcome.class);
                startActivity(back);
            }
        });

    }

    private boolean isValidPassword(String strPass) {
        if (strPass.length() < 8 || strPass.length() > 20) {
            return false; // Check length
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        boolean hasWhitespace = false;

        for (char c : strPass.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if ("!@#$%^&+=*".contains(String.valueOf(c))) hasSpecialChar = true;
            else if (Character.isWhitespace(c)) hasWhitespace = true;
        }

        // Final check for all conditions
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar && !hasWhitespace;
    }
}