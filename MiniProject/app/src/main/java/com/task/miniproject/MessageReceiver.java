package com.task.miniproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

public class MessageReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "ChatChannel";
    private static final int NOTIFICATION_ID = 102;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String sender = intent.getStringExtra("sender");
        String receiver = intent.getStringExtra("receiver");


        showNotification(context, message);
    }

    private void showNotification(Context context, String message) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Chat Notifications", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_final)
                .setContentTitle("Scheduled Message Sent")
                .setContentText("Your message: " + message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();


        nm.notify(NOTIFICATION_ID, notification);
    }
}
