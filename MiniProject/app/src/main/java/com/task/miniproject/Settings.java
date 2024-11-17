package com.task.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Settings extends AppCompatActivity {
    EditText phno,city,area,email,name;
    Spinner sp;
    RadioButton radio1,radio2,radio3,radio4,radio5,radio6,radio7;
    Button update;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        phno=findViewById(R.id.et_phone);
        city=findViewById(R.id.et_city);
        area=findViewById(R.id.et_area);
        sp=findViewById(R.id.sp_occupation);
        email=findViewById(R.id.et_email);
        name=findViewById(R.id.et_name);
        update=findViewById(R.id.btn_submit);


        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String uname = sharedPreferences.getString("username", null);
        Log.d("username in settings",uname);

        DbConnect dbConnect = new DbConnect(this);
        db=dbConnect.getReadableDatabase();
        Matching m= new Matching(db);
        Log.d("matching object","created");
        User u=m.getUserDetails(uname);
        Log.d("settings","user object retrieved");

        name.setText(u.getName());
        email.setText(u.getEmail());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phno_str=phno.getText().toString();
                String city_str=city.getText().toString();
                String area_str=area.getText().toString();
                String job_str=sp.getSelectedItem().toString();

                if(!phno_str.isEmpty()){
                    u.setContact(phno_str);
                }


                if(!city_str.isEmpty()){
                    u.setCity(city_str);
                }
                if(!area_str.isEmpty()){
                    u.setArea(area_str);
                }
                if(!job_str.isEmpty()){
                    u.setJob(job_str);
                }


                dbConnect.updateUserDetails(u);
                Log.d("settings","updated");

                Toast.makeText(Settings.this, "Saved!", Toast.LENGTH_SHORT).show();
                Intent next= new Intent(Settings.this, ProfileMenu.class);
                startActivity(next);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}