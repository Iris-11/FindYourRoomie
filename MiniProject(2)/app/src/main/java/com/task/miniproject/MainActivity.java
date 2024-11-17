package com.task.miniproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Button next;
    TextView txt;
    ImageButton upload;
    private static final int GET_FROM_GALLERY = 1;
    ImageView img,logo, date;
    EditText name,contact,dob,age,city,area;
    RadioButton radio1,radio2,radio3;
    int y,m,d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        next=(Button) findViewById(R.id.btn1);
        txt = findViewById(R.id.textView);
        upload=findViewById(R.id.imgbtn1);
        img=findViewById(R.id.imageView5);
        name=findViewById(R.id.name);
        contact=findViewById(R.id.phone);
        dob=findViewById(R.id.dob);
        age=findViewById(R.id.age);
        city=findViewById(R.id.city);
        area=findViewById(R.id.area);
        radio1=findViewById(R.id.radioButton);
        radio2=findViewById(R.id.radioButton2);
        radio3=findViewById(R.id.radioButton3);
        logo=findViewById(R.id.imageView2);
        date=findViewById(R.id.imageView6);


        logo.setImageResource(R.drawable.logo);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

        SpannableStringBuilder ssb = new SpannableStringBuilder("Basic details ");

        ssb.setSpan(
                new ImageSpan(this, resizedBitmap),
                ssb.length() - 1,
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        txt.setText(ssb);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isValid=true;
                String name_str = name.getText().toString();
                String contact_str = contact.getText().toString();
                String dob_str = dob.getText().toString();
                String age_str = age.getText().toString();
                String city_str=city.getText().toString();
                String area_str=area.getText().toString();



                if (name_str.isEmpty() ||  contact_str.isEmpty() || dob_str.isEmpty() || age_str.isEmpty() || city_str.isEmpty() || area_str.isEmpty()) {
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                int ageValue = 0;
                try {

                    ageValue = Integer.parseInt(age_str);
                    if(getAge()!=ageValue){
                        age.setError("Birth date and age do not match");
                    }
                    if (ageValue < 18) {
                        age.setError("You must be 18 years or older");
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    age.setError("Enter valid age");
                    isValid = false;
                }

                if (contact_str.length() < 10) {
                    contact.setError("Enter valid contact number");
                    isValid = false;
                }

                if (!radio1.isChecked() && !radio2.isChecked() && !radio3.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if (isValid) {
                    String gender="";
                    if(radio1.isChecked()){
                        gender="Female";
                    }
                    else if(radio2.isChecked()){
                        gender="Male";
                    }
                    else if(radio3.isChecked()){
                        gender="Other";
                    }
                    int age=Integer.parseInt(age_str);
                    Intent nextPage = new Intent(MainActivity.this, MainActivity2.class);
                    nextPage.putExtra("city",city_str);
                    nextPage.putExtra("area",area_str);
                    nextPage.putExtra("dob",dob_str);
                    nextPage.putExtra("name",name_str);
                    nextPage.putExtra("contact",contact_str);
                    nextPage.putExtra("age",age);
                    nextPage.putExtra("gender",gender);
                    Intent prev=getIntent();
                    nextPage.putExtra("email",prev.getStringExtra("email"));


                    startActivity(nextPage);
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Delhi");
                Calendar calendar = Calendar.getInstance(timeZone);
                y = calendar.get(Calendar.YEAR);
                m = calendar.get(Calendar.MONTH);
                d = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        dob.setText(year+"-"+String.format("%02d",month+1)+"-"+String.format("%02d",dayOfMonth));

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth, 0, 0, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }


                },y,m,d);
                dialog.show();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private int getAge(){
        int year_diff = Calendar.getInstance().get(Calendar.YEAR) - y;
        int month_diff = Calendar.getInstance().get(Calendar.MONTH) - m;
        int ageOfUser = (year_diff * 365) + (month_diff *31);
        ageOfUser = Math.floorDiv(ageOfUser,365);
        if(ageOfUser<18){
            Toast.makeText(getApplicationContext(),"Cannot be less than 18!",Toast.LENGTH_LONG);
            return -1;
        }
        return ageOfUser;
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}