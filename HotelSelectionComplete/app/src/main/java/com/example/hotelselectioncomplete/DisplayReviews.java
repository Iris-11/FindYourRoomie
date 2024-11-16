package com.example.hotelselectioncomplete;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DisplayReviews extends AppCompatActivity {
    ListView lv;
    ArrayList<String> al;
    ReviewForHostelDb dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "onCreate started");

        // Set the activity layout
        setContentView(R.layout.activity_display_reviews);
        Log.d("Layout", "Layout set");

        // Initialize the ListView
        lv = findViewById(R.id.list_view);
        if (lv != null) {
            Log.d("ListView", "ListView initialized");
        } else {
            Log.e("ListView", "ListView initialization failed");
        }

        // Initialize the ArrayList and database helper
        al = new ArrayList<>();
        dbhelper = new ReviewForHostelDb(this);
        Log.d("Database", "Database helper initialized");

        // Handle intent to fetch hostel name
        Intent intent = getIntent();
        Log.d("Intent", "Checking for intent");

        if (intent != null) {
            Log.d("Intent", "Intent is not null");
            String name = intent.getStringExtra("name_of_hostel");
            Log.d("Hostel Name", "Received: " + name);

            if (name != null) {
                Log.d("Database Query", "Querying reviews for hostel: " + name);

                // Fetch reviews for the hostel
                Cursor c = dbhelper.returnHostelReviews(name);

                if (c != null) {
                    Log.d("Cursor", "Cursor is not null, iterating over results");
                    while (c.moveToNext()) {
                        String text = c.getString(c.getColumnIndexOrThrow(ReviewForHostelDb.COL_TEXT));
                        al.add(text);
                        Log.d("Review Added", "Review: " + text);
                    }
                    c.close(); // Close the cursor after use
                    Log.d("Cursor", "Cursor closed");
                } else {
                    Log.e("Cursor", "Cursor is null, no data found");
                }
            } else {
                Log.e("Intent Data", "Hostel name is null");
            }
        } else {
            Log.e("Intent", "Intent is null");
        }

        // Set up the adapter for the ListView
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(ad);
        Log.d("Adapter", "Adapter set with data size: " + al.size());

        // Adjust padding for system bars (optional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Log.d("Insets", "System bars padding applied");
            return insets;
        });

        Log.d("Lifecycle", "onCreate completed");
    }
}
