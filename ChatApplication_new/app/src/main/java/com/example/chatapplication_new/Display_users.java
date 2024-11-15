package com.example.chatapplication_new;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Display_users extends AppCompatActivity {
    DBHelper db;
    ListView list;
    ArrayList<String> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_users);

        // Initialize DBHelper
        db = new DBHelper(this);

        Intent intent = getIntent();
        al = new ArrayList<>();
        if (intent != null) {
            String id = intent.getStringExtra("incoming_id");
            list = findViewById(R.id.list);

            // Ensure the database instance is properly initialized
            Cursor c = db.getNames(id);
            if (c != null) {
                while (c.moveToNext()) { // Iterate through all the results
                    String col = c.getString(c.getColumnIndexOrThrow(DBHelper.OUTGOING_MESSAGE_ID));
                    al.add(col);
                }
                c.close(); // Close cursor to prevent memory leaks
            }
        }

        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);
        list.setAdapter(ad);
        list.setVisibility(View.VISIBLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_chat = al.get(position);
                Intent intent2 = new Intent(Display_users.this, MainActivity.class);
                intent2.putExtra("incoming", id);
                intent2.putExtra("outgoing", selected_chat);
                startActivity(intent2);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}