package com.example.hotelselectioncomplete;



import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReviewForHostel extends AppCompatActivity {
    RatingBar rb;
    Button btn;
    TextView t1, t2, t3; // for the review text
    ReviewForHostelDb dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_for_hostel);

        // Initialize views
        rb = findViewById(R.id.ratingBar);
        btn = findViewById(R.id.review_submit_btn);
        t1 = findViewById(R.id.editTextText);
        t2 = findViewById(R.id.editTextText2);
        t3 = findViewById(R.id.editTextText3);

        // Initialize the database helper
        dbHelper = new ReviewForHostelDb(this);

        // Adjust system bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set onClickListener for the button
        btn.setOnClickListener(view -> {
            // Get values from input fields
            String rating = String.valueOf(rb.getRating());
            String reviewText = t1.getText().toString();
            String hostelName = t2.getText().toString();
            String hostelCity = t3.getText().toString();

            // Validate inputs
            if (hostelName.isEmpty() || hostelCity.isEmpty() || reviewText.isEmpty()) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            } else {
                // Insert review into the database
                dbHelper.insertReview(db,hostelName, hostelCity, reviewText, rating);
                Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();

                // Optionally, clear input fields after submission
                rb.setRating(0);
                t1.setText("");
                t2.setText("");
                t3.setText("");
            }
        });
    }
}
