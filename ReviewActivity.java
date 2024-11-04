package com.example.hotelselectioncomplete;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    ListView list_view;
    EditText ed;
    DBHelper db;
    Button btn,submitBtn;
    ArrayList <String> reviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review);
        list_view=findViewById(R.id.reviewListView);
        db=new DBHelper(this);
        reviews=new ArrayList<>();
        btn=(Button) findViewById(R.id.backButton);
        submitBtn=(Button) findViewById(R.id.submitReviewButton);
        ed=findViewById(R.id.reviewEditText);
        int hostel_id_current= getIntent().getIntExtra("hostel_id",-1);
        Cursor cursor=db.getReviewsForHostel(hostel_id_current);
        while(cursor.moveToNext()){
            reviews.add(cursor.getString(2));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reviews);
        list_view.setAdapter(adapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reviewListView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        submitBtn.setOnClickListener(v -> {
            int hostelId = Integer.valueOf(getIntent().getIntExtra("hostel_id", -1));
            String reviewText = ed.getText().toString();
            db.insertReview(hostelId, reviewText);
            ed.setText("");
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}