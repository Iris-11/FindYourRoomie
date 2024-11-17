package com.task.miniproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String sender = intent.getStringExtra("sender");
        String receiver = intent.getStringExtra("receiver");

        // Insert the message as a normal chat message (mark it as sent)
        ChatDbHelper dbHelper = new ChatDbHelper(context);
        dbHelper.insertChat(sender, receiver, message, null); // Now it's a normal sent message

        // Optionally, notify the user (for UI update purposes)
        Intent updateIntent = new Intent("com.task.miniproject.MESSAGE_SENT");
        context.sendBroadcast(updateIntent);
    }
}