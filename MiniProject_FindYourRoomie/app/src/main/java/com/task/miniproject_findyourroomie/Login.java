package com.task.miniproject_findyourroomie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button loginBtn;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        error = findViewById(R.id.errorInfoLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbConnect conn = new DbConnect(getApplicationContext());

                String strUname = username.getText().toString().trim();
                String strPwd = password.getText().toString().trim();

                if (strPwd.isEmpty() || strUname.isEmpty()) {
                    error.setText("All fields required!");
                    error.setTextColor(Color.parseColor("#F44336"));
                    return;
                } else {
                    User thisUser = conn.getUserByName(strUname);
                    if (thisUser == null) {
                        error.setText("Username does not exist!");
                        error.setTextColor(Color.parseColor("#F44336"));
                        return;
                    } else {
                        System.out.println(thisUser.getUsername()+" pwd:"+ Arrays.toString(thisUser.getPassword()) +" salt:"+ Arrays.toString(thisUser.getSalt()));
                        try {
                            byte[] storedHashedPwd = thisUser.getPassword();
                            byte[] storedSalt = thisUser.getSalt();
                            if(conn.passwordMatch(strPwd,storedSalt,storedHashedPwd)){
                                Toast.makeText(getApplicationContext(), "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                                // Intent to Home activity instead of ProfileMenu
                                Intent i = new Intent(getApplicationContext(), ProfileMenu.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                error.setText("Incorrect Password!");
                                error.setTextColor(Color.parseColor("#F44336"));
                                return;
                            }
                        } catch (Exception e) {
                            error.setText("An error occurred. Please try again.");
                            error.setTextColor(Color.parseColor("#F44336"));
                        }
                    }
                }
            }
        });

    }
}