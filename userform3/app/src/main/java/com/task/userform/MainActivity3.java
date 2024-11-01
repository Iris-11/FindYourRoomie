package com.task.userform;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    Button next;
    TextView txt;
    Spinner age_range,occupation;
    RadioButton radio1,radio2,radio3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        next=findViewById(R.id.btn);
        txt=findViewById(R.id.text1);
        age_range=findViewById(R.id.spinner_age);
        occupation=findViewById(R.id.spinner_job);
        radio1=findViewById(R.id.rbtn1);
        radio2=findViewById(R.id.rbtn2);
        radio3=findViewById(R.id.rbtn3);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

        SpannableStringBuilder ssb = new SpannableStringBuilder("Roomie preferences");
        ssb.setSpan(
                new ImageSpan(this, resizedBitmap),
                ssb.length() - 1,  // Start of the image span (the space we added)
                ssb.length(),      // End of the image span
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        txt.setText(ssb);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String age_range_str=age_range.getSelectedItem().toString();
                String occupation_str=occupation.getSelectedItem().toString();
                boolean gender_pref=radio1.isChecked()||radio2.isChecked()|| radio3.isChecked();
                if(age_range_str.isEmpty()|| occupation_str.isEmpty()|| !gender_pref){
                    Toast.makeText(MainActivity3.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent nextPage=new Intent(MainActivity3.this,MainActivity4.class);
                    startActivity(nextPage);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}