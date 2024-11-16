package com.example.hotelselectioncomplete;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TakeHostelName extends AppCompatActivity {
    EditText t1;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_hostel_name);

        // Initialize views
        t1 = findViewById(R.id.editTextText4); // Ensure the ID matches your XML
        submit_btn = findViewById(R.id.button);

        // Log and handle button click
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = t1.getText().toString().trim(); // Capture text on button click
                Log.d("HostelName", "Captured hostel name: " + name);

                if (!name.isEmpty()) {
                    Intent intent = new Intent(TakeHostelName.this, DisplayReviews.class);
                    intent.putExtra("name_of_hostel", name);
                    startActivity(intent);
                } else {
                    Log.e("HostelName", "Hostel name is empty");
                    t1.setError("Please enter a hostel name"); // Set error if the field is empty
                }
            }
        });

        // Adjust padding for system bars (optional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
