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
                String strUname = username.getText().toString();
                String strPwd = username.getText().toString();
                if(strPwd.isEmpty() || strUname.isEmpty()){
                    error.setText("All fields required!");
                    error.setTextColor(Color.parseColor("#F44336"));
                    return;
                }
                else{
                    DbConnect conn = new DbConnect(getApplicationContext());
                    User thisUser = conn.getUserByName(strUname);
                    if(thisUser==null){
                        error.setText("Username does not exist!");
                        error.setTextColor(Color.parseColor("#F44336"));
                        return;
                    }
                    else{
                        String storedHashedPwd = thisUser.getPassword();
                        String storedSalt = thisUser.getSalt();
                        String[] hashedEntered = conn.encrypt(strPwd,storedSalt);
                        String hashedPwd = hashedEntered[1];
                        if(!hashedPwd.equals(storedHashedPwd)){
                            error.setText("Incorrect Password!");
                            error.setTextColor(Color.parseColor("#F44336"));
                            return;
                        }
                        else{
                            /*Toast.makeText(getApplicationContext(), "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                            finish(); getting issue of no toast and redirection to welcome instead of home*/
                            Toast.makeText(Login.this, "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(getApplicationContext(), Home.class);
                            startActivity(i2);
                            finish();

                        }
                    }
                }
            }
        });
    }
}