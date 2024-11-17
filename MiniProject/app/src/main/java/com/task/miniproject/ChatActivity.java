package com.task.miniproject;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.miniproject.ChatAdapter;
import com.task.miniproject.DbHelper;
import com.task.miniproject.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText usernameEditText, messageEditText;
    private RecyclerView chatRecyclerView;
    private DbHelper databaseHelper;
    private ChatAdapter chatAdapter;
    private List<ChatAdapter.ChatMessage> messageList;

    private static final String CHANNEL_ID = "ForumChannel";
    private static final int NOTIFICATION_ID = 100;
    private NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        usernameEditText = findViewById(R.id.usernameEditText);
        messageEditText = findViewById(R.id.messageEditText);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        usernameEditText.setText(username);

        databaseHelper = new DbHelper(this);
        messageList = new ArrayList<>();

        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setAdapter(chatAdapter);

        loadMessages();

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String message = messageEditText.getText().toString();

                if (!username.isEmpty() && !message.isEmpty()) {
                    databaseHelper.getWritableDatabase().execSQL(
                            "INSERT INTO " + DbHelper.TABLE_NAME + " (" +
                                    DbHelper.COLUMN_USERNAME + ", " +
                                    DbHelper.COLUMN_MESSAGE + ") VALUES ('" +
                                    username + "', '" + message + "')");


                    messageList.add(new ChatAdapter.ChatMessage(username, message));
                    chatAdapter.notifyDataSetChanged();

                    messageEditText.setText("");

                    showNotifications();
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter both username and message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadMessages() {

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

    private void showNotifications() {

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.meeting, null);
        Bitmap largeIcon = null;

        if (drawable instanceof BitmapDrawable) {
            largeIcon = ((BitmapDrawable) drawable).getBitmap();
        }

        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Forum Notifications", NotificationManager.IMPORTANCE_HIGH);
            if (nm != null && nm.getNotificationChannel(CHANNEL_ID) == null) {
                nm.createNotificationChannel(channel);
            }


            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.logo_final)
                    .setContentText("Message posted!")
                    .setSubText("Your message is posted in the forum")
                    .build();
        } else {

            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.logo_final)
                    .setContentText("Message posted!")
                    .setSubText("Your message is posted in the forum")
                    .build();
        }


        if (nm != null) {
            nm.notify(NOTIFICATION_ID, notification);
        }
    }
}
