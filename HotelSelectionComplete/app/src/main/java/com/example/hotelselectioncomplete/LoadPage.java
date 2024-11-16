package com.example.hotelselectioncomplete;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoadPage extends AppCompatActivity {
    TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load_page);
        t2 = findViewById(R.id.t2);

        Intent intent = getIntent();
        String str = intent.getStringExtra("place");
        String finalStr="Looking for hostels in " + str + "...";
        t2.setText(finalStr);
        //Intent intent2=new Intent(LoadPage.this,HostelSelectionActivity.class);
        // Introduce a delay of 3 seconds (3000 milliseconds) before starting the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = new Intent(LoadPage.this, HostelSelectionActivity.class);
                intent2.putExtra("location", str);

                startActivity(intent2);
                finish(); // Close the LoadPage activity after starting the next one
            }
        }, 3000); // 3000 ms = 3 seconds


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}