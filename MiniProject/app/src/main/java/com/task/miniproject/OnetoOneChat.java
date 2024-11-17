package com.task.miniproject;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class OnetoOneChat extends AppCompatActivity {

    EditText messageInput;
    Button sendButton, scheduleButton;
    LinearLayout chatBox;
    ChatDbHelper dbHelper;
    TextView chatName;
    String incomingId, outgoingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneto_one_chat);

        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        chatBox = findViewById(R.id.chatBox);
        chatName = findViewById(R.id.chatHeader);
        scheduleButton = findViewById(R.id.scheduleButton);
        dbHelper = new ChatDbHelper(this);

        incomingId = getIntent().getStringExtra("sender");
        outgoingId = getIntent().getStringExtra("receiver");
        chatName.setText(outgoingId);
        Log.d("user", "retrieved");

        loadChatMessages(incomingId, outgoingId);

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                dbHelper.insertChat(incomingId, outgoingId, message, null);
                addMessage(message, true); // true means outgoing
                messageInput.setText(""); // clear the input
            }
        });

        scheduleButton.setOnClickListener(v -> scheduleMessage());
    }

    private void loadChatMessages(String incomingId, String outgoingId) {
        Cursor cursor = dbHelper.getChats(incomingId, outgoingId);
        if (cursor.moveToFirst()) {
            do {
                String msg = cursor.getString(cursor.getColumnIndexOrThrow("msg"));
                String sender = cursor.getString(cursor.getColumnIndexOrThrow("incoming_msg_id"));
                String receiver = cursor.getString(cursor.getColumnIndexOrThrow("outgoing_msg_id"));
                Log.d("ChatDebug", "Message: " + msg + ", Sender: " + sender + ", Receiver: " + receiver);

                boolean isOutgoing = cursor.getString(cursor.getColumnIndexOrThrow("outgoing_msg_id")).equals(outgoingId);

                if (isOutgoing) {
                    addMessage(msg, true);
                } else {
                    addMessage(msg, false);
                }

            } while (cursor.moveToNext());
        }
    }

    private void addMessage(String message, boolean isSent) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextSize(16);
        textView.setTextColor(isSent ? getResources().getColor(android.R.color.black) : getResources().getColor(android.R.color.black));
        textView.setBackgroundResource(isSent ? R.drawable.sent_message_background : R.drawable.received_message_background);
        textView.setPadding(20, 10, 20, 10);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        params.gravity = isSent ? Gravity.END : Gravity.START;
        textView.setLayoutParams(params);

        chatBox.addView(textView);
    }

    private void scheduleMessage() {
        String message = messageInput.getText().toString();
        if (message.isEmpty()) {
            Toast.makeText(this, "Enter a message to schedule", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar = Calendar.getInstance();

        // Show DatePickerDialog
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Show TimePickerDialog
            new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                long scheduleTime = calendar.getTimeInMillis();
                if (scheduleTime > System.currentTimeMillis()) {


                    dbHelper.insertChat(incomingId, outgoingId, message, scheduleTime);
                    scheduleAlarm(scheduleTime, message);
                    messageInput.setText("");
                    Toast.makeText(this, "Message scheduled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cannot schedule a message in the past", Toast.LENGTH_SHORT).show();
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void scheduleAlarm(long scheduleTime, String message) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        String requestCodeStr = incomingId + "_" + outgoingId + "_" + scheduleTime;
        int requestCode = requestCodeStr.hashCode();


        Intent cancelIntent = new Intent(this, MessageReceiver.class);
        cancelIntent.putExtra("message", message);
        cancelIntent.putExtra("sender", incomingId);
        cancelIntent.putExtra("receiver", outgoingId);

        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this, requestCode, cancelIntent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
        if (cancelPendingIntent != null) {
            alarmManager.cancel(cancelPendingIntent);
        }


        Intent intent = new Intent(this, MessageReceiver.class);
        intent.putExtra("message", message);
        intent.putExtra("sender", incomingId);
        intent.putExtra("receiver", outgoingId);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, scheduleTime, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, scheduleTime, pendingIntent);
        }
    }

}
