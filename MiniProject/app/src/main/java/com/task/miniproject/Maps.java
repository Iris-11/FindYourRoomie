package com.task.miniproject;

//Get_google_maps.java activity
import static androidx.core.content.ContextCompat.startActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Maps extends AppCompatActivity {
    EditText area,city;
    Button go;
    ImageButton backbtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);

        area = findViewById(R.id.areaEditText);
        city = findViewById(R.id.cityEditText);
        go = findViewById(R.id.button2);
        backbtn2 = findViewById(R.id.imageButton2);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ar = area.getText().toString();
                String cty = city.getText().toString();

                String query = "geo:0,0?q="+ar+" "+cty+" hostels";

                Uri gmmIntentUri = Uri.parse(query);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        backbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileMenu.class);
                startActivity(intent);
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
