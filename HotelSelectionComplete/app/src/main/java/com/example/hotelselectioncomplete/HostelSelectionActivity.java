package com.example.hotelselectioncomplete;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class HostelSelectionActivity extends AppCompatActivity {
    ListView list;
    Button btn;
    TextView t1;
    DBHelper db;
    ArrayList <hostel> hostel_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hostel_selection);
        Intent intent=getIntent();
        if (intent != null) {
            String loc = intent.getStringExtra("location");
            list=findViewById(R.id.hostelListView);
            t1=findViewById(R.id.t1);
            btn=findViewById(R.id.backButton);
            db=new DBHelper(this);
            hostel_list=new ArrayList<>();
            Cursor cursor= db.filterHostelFromPlace(loc);

            Log.d("HostelSelectionActivity", "Location filter: " + loc);

            if (cursor != null ) {
                while (cursor.moveToNext()) {
                    String hostelId = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.hostel_id));
                    String hostelName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.hostel_name));
                    String hostelContact = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.contact));
                    String hostelLocation = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.location));

                    hostel_list.add(new hostel(hostelId, hostelName, hostelContact, hostelLocation));

                    // Log each hostel to see if data is being retrieved
                    Log.d("HostelSelectionActivity", "Hostel retrieved: " + hostelName);
                }
            } else {
                Log.d("HostelSelectionActivity", "No hostels found for location: " + loc);
            }
            if (cursor != null) cursor.close();

            ArrayAdapter<hostel> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hostel_list);

            list.setAdapter(ad);
            list.setVisibility(View.VISIBLE);
            Log.d("HostelSelectionActivity", "ListView visibility: " + list.getVisibility());
            Log.d("HostelSelectionActivity", "Number of hostels found: " + cursor.getCount());

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    hostel selected_hostel=hostel_list.get(position);
                    String selected_id= selected_hostel.id;
                    Intent intent =new Intent(HostelSelectionActivity.this, ReviewActivity.class);
                    intent.putExtra("hostel_id", selected_id);
                    startActivity(intent);


                }

            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hostelListView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}