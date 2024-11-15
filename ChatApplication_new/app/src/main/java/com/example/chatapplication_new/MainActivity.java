package com.example.chatapplication_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText messageInput;
    Button sendButton;
    LinearLayout chatBox;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        chatBox = findViewById(R.id.chatBox);
        dbHelper = new DBHelper(this);

       String incomingId = getIntent().getStringExtra("incoming");
        String outgoingId = getIntent().getStringExtra("outgoing");
        /*String incomingId="2";
        String outgoingId="1";*/
        loadChatMessages(incomingId,outgoingId);

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                dbHelper.insertChat(incomingId, outgoingId, message);
                displayMessage(message, true);  // true means outgoing
                messageInput.setText("");  // clear the input
            }
        });
    }

    private void loadChatMessages(String incomingId, String outgoingId) {
        Cursor cursor = dbHelper.getChats(incomingId, outgoingId);
        if (cursor.moveToFirst()) {
            do {
                String msg = cursor.getString(cursor.getColumnIndexOrThrow("msg"));
                boolean isOutgoing = cursor.getString(cursor.getColumnIndexOrThrow("outgoing_msg_id")).equals(outgoingId);
                displayMessage(msg, isOutgoing);
            } while (cursor.moveToNext());
        }
    }

    private void displayMessage(String message, boolean isOutgoing) {
        TextView textView = new TextView(this);
        textView.setText(message);
      //  textView.setBackgroundResource(isOutgoing ? R.drawable.chat : R.drawable.message);
        chatBox.addView(textView);
    }
   /* SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
    String email = sharedPreferences.getString("email", null);

    if (email == null) {
        // Redirect to login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }*/

}
