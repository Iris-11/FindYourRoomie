package com.example.hotelselectioncomplete;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class MainActivity extends AppCompatActivity {
    private ChatDb dbHelper;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private EditText editTextMessage;
    private ArrayList<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_application);

        dbHelper = new ChatDb(this);

        recyclerView = findViewById(R.id.recyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);

        messageList = new ArrayList<>();
        loadMessages();

        messageAdapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonSend.setOnClickListener(v -> {
            String messageText = editTextMessage.getText().toString().trim();
            if (!messageText.isEmpty()) {
                String timestamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                dbHelper.addMessage(messageText, "User", timestamp);
                editTextMessage.setText("");

                loadMessages();
                messageAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadMessages() {
        Cursor cursor = dbHelper.getAllMessages();
        messageList.clear();
        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String sender = cursor.getString(cursor.getColumnIndexOrThrow("sender"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                messageList.add(new Message(message, sender, timestamp));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
