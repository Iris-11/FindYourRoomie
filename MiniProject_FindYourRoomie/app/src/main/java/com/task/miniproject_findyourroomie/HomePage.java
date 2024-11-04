package com.task.miniproject_findyourroomie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePage extends AppCompatActivity {
    TextView description, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        description=findViewById(R.id.desc);
        //back=findViewById(R.id.backbtn);

        description.setText("Find Your Roomie: The Ultimate Roommate Finder App\n" +
                "\n" +
                "Are you searching for the perfect roommate to share your space with? Look no further! Find Your Roomie is designed to simplify your search for compatible roommates and living arrangements, making the process seamless and enjoyable.\n" +
                "\n" +
                "With our user-friendly interface, you can easily create a profile and fill out a detailed user information form, specifying your preferences for lifestyle, interests, and living habits. Our intelligent matching algorithm analyzes your inputs to suggest potential roommates who fit your criteria.");

      /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backPage=new Intent(HomePage.this, ProfileMenu.class);
                startActivity(backPage);
            }
        });*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}