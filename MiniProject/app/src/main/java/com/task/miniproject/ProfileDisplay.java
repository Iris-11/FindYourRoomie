package com.task.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileDisplay extends AppCompatActivity {
    ImageButton back;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_display);


        back=findViewById(R.id.btnback);
        display=findViewById(R.id.details);


        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String uname = sharedPreferences.getString("username", null); // Default to null if not found
        Log.d("uanme",uname);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home=new Intent(ProfileDisplay.this, ProfileMenu.class);
                startActivity(home);
            }
        });

        DbConnect dbconnect=new DbConnect(this);
        SQLiteDatabase db= dbconnect.getReadableDatabase();
        Matching m=new Matching(db);
        User u=m.getUserDetails(uname);


        StringBuilder sb=new StringBuilder();
        sb.append("Name: ").append(u.getName()).append("\n")
                .append("Email: ").append(u.getEmail()).append("\n")
                .append("Contact: ").append(u.getContact()).append("\n")
                .append("Stay city: ").append(u.getCity()).append("\n")
                .append("stay area: ").append(u.getArea()).append("\n");
        display.setText(sb.toString());

        //  display.setText(u.getName());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}