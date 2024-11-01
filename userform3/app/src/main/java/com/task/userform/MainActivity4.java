package com.task.userform;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity4 extends AppCompatActivity {
TextView txt;
RadioButton radio1,radio2,radio3,radio4,radio5,radio6,radio7;
Button save;
AlertDialog.Builder builder;
CheckBox agree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);

        txt=findViewById(R.id.text1);
        radio1=findViewById(R.id.radio1);
        radio2=findViewById(R.id.radio2);
        radio3=findViewById(R.id.radio3);
        radio4=findViewById(R.id.radio4);
        radio5=findViewById(R.id.radio5);
        radio6=findViewById(R.id.radio6);
        radio7=findViewById(R.id.radio7);
        save=findViewById(R.id.button);
        agree=findViewById(R.id.checkBox);

        builder = new AlertDialog.Builder(this);

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean agree_box=agree.isChecked();
                if(!agree_box){
                    Toast.makeText(MainActivity4.this, "click the checkbox", Toast.LENGTH_SHORT).show();
                }
                else{
                    builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_name)
                            .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Intent newPage = new Intent(MainActivity4.this,com.task.popupmenu.MainActivity.class);

                                }
                            })
                            .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
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