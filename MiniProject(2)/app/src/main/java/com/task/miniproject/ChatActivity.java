package com.task.miniproject;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText usernameEditText, messageEditText;
    private RecyclerView chatRecyclerView;
    private DbHelper databaseHelper;
    private ChatAdapter chatAdapter;
    private List<ChatAdapter.ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        messageEditText = findViewById(R.id.messageEditText);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        usernameEditText.setText(username);
        // Initialize database helper and message list
        databaseHelper = new DbHelper(this);
        messageList = new ArrayList<>();

        // Set up adapter
        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setAdapter(chatAdapter);

        // Load existing messages
        loadMessages();

        // Send button click listener
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String message = messageEditText.getText().toString();

                if (!username.isEmpty() && !message.isEmpty()) {
                    // Insert new message into database
                    databaseHelper.getWritableDatabase().execSQL(
                            "INSERT INTO " + DbHelper.TABLE_NAME + " (" +
                                    DbHelper.COLUMN_USERNAME + ", " +
                                    DbHelper.COLUMN_MESSAGE + ") VALUES ('" +
                                    username + "', '" + message + "')");

                    // Add message to list and update RecyclerView
                    messageList.add(new ChatAdapter.ChatMessage(username, message));
                    chatAdapter.notifyDataSetChanged();

                    // Clear message input field
                    messageEditText.setText("");
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter both username and message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadMessages() {
        // Load messages from the database
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DbHelper.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USERNAME));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_MESSAGE));
                messageList.add(new ChatAdapter.ChatMessage(username, message));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}