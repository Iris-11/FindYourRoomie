package com.task.miniproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileDisplay extends AppCompatActivity {
    ImageButton back;
    TextView display;
    Button del;
    SQLiteDatabase db;
    DbConnect dbconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        back = findViewById(R.id.btnback);
        display = findViewById(R.id.details);
        del = findViewById(R.id.button6);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String uname = sharedPreferences.getString("username", null);

        if (uname == null) {
            Toast.makeText(this, "No username found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("uname", uname);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(ProfileDisplay.this, ProfileMenu.class);
                startActivity(home);
            }
        });

        dbconnect = new DbConnect(this);
        db = dbconnect.getReadableDatabase();
        Matching m = new Matching(db);
        User u = m.getUserDetails(uname);

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(u.getName()).append("\n")
                .append("Email: ").append(u.getEmail()).append("\n")
                .append("Contact: ").append(u.getContact()).append("\n")
                .append("Stay city: ").append(u.getCity()).append("\n")
                .append("Stay area: ").append(u.getArea()).append("\n");
        display.setText(sb.toString());

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextName1 = new EditText(ProfileDisplay.this);
                AlertDialog.Builder alertName = new AlertDialog.Builder(ProfileDisplay.this);
                alertName.setTitle("Want to delete your account? Type in 'Delete' to confirm");
                alertName.setView(editTextName1);

                alertName.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String confirm = editTextName1.getText().toString();
                        if (confirm.trim().equalsIgnoreCase("delete")) {
                            int rows = db.delete(dbconnect.getTableName(), "email=?", new String[]{u.getEmail()});
                            if (rows > 0) {
                                Toast.makeText(ProfileDisplay.this, "Your data has been deleted", Toast.LENGTH_SHORT).show();
                                Intent welcome = new Intent(ProfileDisplay.this, Welcome.class);
                                startActivity(welcome);
                                finish();
                            } else {
                                Toast.makeText(ProfileDisplay.this, "Delete failed, please try again later!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileDisplay.this, "Please enter 'Delete' to confirm deletion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alertName.show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
        if (dbconnect != null) {
            dbconnect.close();
        }
    }
}
