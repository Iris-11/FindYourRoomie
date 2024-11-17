package com.task.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DisplayMatches extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_matches);

        ImageButton back = findViewById(R.id.btnback);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        DbConnect dbConnect = new DbConnect(this);
        db = dbConnect.getReadableDatabase();
        Matching matching = new Matching(db);

        Intent prev = getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null); // Default to null if not found

        User user = matching.getUserDetails(username);
        List<Match> matches = matching.getCorrectMatches(user);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CardViewAdapter adapter = new CardViewAdapter(matches,DisplayMatches.this);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(view -> {
            Intent prevIntent = new Intent(DisplayMatches.this, ProfileMenu.class);
            prevIntent.putExtra("username", username);
            startActivity(prevIntent);
        });
    }
}
