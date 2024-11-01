package com.task.userform;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private Spinner occupation;
    private ConstraintLayout WorkingProfessional;
    private ConstraintLayout Student;
    Button next;
    TextView txt;
    EditText native_place;
    CheckBox english,hindi,marathi,telugu;
    RadioButton radio1,radio2,radio3,radio4,radio5,radio6,radio7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        occupation = (Spinner) findViewById(R.id.occupation);
        WorkingProfessional = findViewById(R.id.layoutWorkingProfessional);
        Student= findViewById(R.id.layoutStudent);
        next=findViewById(R.id.btn2);
        txt=findViewById(R.id.personal);
        native_place=findViewById(R.id.native_addr);
        english=findViewById(R.id.english);
        hindi=findViewById(R.id.hindi);
        marathi=findViewById(R.id.marathi);
        telugu=findViewById(R.id.telugu);
        radio1=findViewById(R.id.radio1);
        radio2=findViewById(R.id.radio2);
        radio3=findViewById(R.id.radio3);
        radio4=findViewById(R.id.radio4);
        radio5=findViewById(R.id.radio5);
        radio6=findViewById(R.id.radio6);
        radio7=findViewById(R.id.radio7);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);


        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

        SpannableStringBuilder ssb = new SpannableStringBuilder("Personal details ");

        ssb.setSpan(
                new ImageSpan(this, resizedBitmap),
                ssb.length() - 1,  // Start of the image span (the space we added)
                ssb.length(),      // End of the image span
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        txt.setText(ssb);

        occupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOccupation = adapterView.getItemAtPosition(i).toString();

                if (selectedOccupation.equals("Student")){
                    Student.setVisibility(View.VISIBLE);
                    WorkingProfessional.setVisibility(View.GONE);
                }
                else if (selectedOccupation.equals("Working professional")){
                    WorkingProfessional.setVisibility(View.VISIBLE);
                    Student.setVisibility(View.GONE);
                }
                else{
                    Student.setVisibility(View.GONE);
                    WorkingProfessional.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Student.setVisibility(View.GONE);
                WorkingProfessional.setVisibility(View.GONE);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String occ_str = occupation.getSelectedItem().toString();
                String native_str = native_place.getText().toString();

                // Check if at least one checkbox is selected
                boolean languageSelected = english.isChecked() || hindi.isChecked() || marathi.isChecked() || telugu.isChecked();

                // Check if at least one radio button is selected
                boolean radioGroup1Selected = radio1.isChecked() || radio2.isChecked();
                boolean radioGroup2Selected = radio3.isChecked() || radio4.isChecked();
                boolean radioGroup3Selected = radio5.isChecked() || radio6.isChecked() || radio7.isChecked();

                // Validate required fields
                if (occ_str.isEmpty() || native_str.isEmpty() || !languageSelected || !radioGroup1Selected || !radioGroup2Selected || !radioGroup3Selected) {
                    Toast.makeText(MainActivity2.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    Intent nextPage = new Intent(MainActivity2.this, MainActivity3.class);
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