package com.example.hotelselectioncomplete;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class TakeUsername extends AppCompatActivity {
    EditText t1;
    TextView error_t2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.take_username);

        // Initialize DbConnect
        DbConnect dbHelper = new DbConnect(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // UI references
        t1 = findViewById(R.id.t1);
        btn = findViewById(R.id.btn);

        // Button click listener
        btn.setOnClickListener(v -> {
            String u_id = t1.getText().toString().trim();
            if (!u_id.isEmpty()) {
                // Fetch place from the database based on user ID
                String place = dbHelper.extractPlaceFromUserId(u_id, db);
                if (!place.isEmpty()) {
                    // Intent to navigate to LoadPage
                    Intent intent = new Intent(TakeUsername.this, LoadPage.class);
                    intent.putExtra("place", place);
                    startActivity(intent);
                } else {
                    // Set error message when user not found
                    t1.setError("User not found!");
                }
            } else {
                // Set error message when no user ID is entered
                t1.setError("Please enter a valid user ID!");
            }
        });

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
