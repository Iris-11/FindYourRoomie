package com.task.miniproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReviewForHostel extends AppCompatActivity {
    RatingBar rb;
    Button btn;
    TextView t1, t2, t3; // for the review text
    ReviewForHostelDb dbHelper;
    SQLiteDatabase db;

    private static final String CHANNEL_ID = "ReviewChannel";
    private static final int NOTIFICATION_ID = 101;
    private NotificationManager nm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_for_hostel);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


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

                showNotifications();
                // Optionally, clear input fields after submission
                rb.setRating(0);
                t1.setText("");
                t2.setText("");
                t3.setText("");
            }
        });
    }
    private void showNotifications() {

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.reviews, null);
        Bitmap largeIcon = null;

        if (drawable instanceof BitmapDrawable) {
            largeIcon = ((BitmapDrawable) drawable).getBitmap();
        }

        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Review Notifications", NotificationManager.IMPORTANCE_HIGH);
            if (nm != null && nm.getNotificationChannel(CHANNEL_ID) == null) {
                nm.createNotificationChannel(channel);
            }


            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.logo_final)
                    .setContentText("Review posted!")
                    .setSubText("Your review is posted in the reviews section!")
                    .build();
        } else {

            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.logo_final)
                    .setContentText("Review posted!")
                    .setSubText("Your review is posted in the reviews section!")
                    .build();
        }


        if (nm != null) {
            nm.notify(NOTIFICATION_ID, notification);
        }
    }

}