package com.task.miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChatDB.db";
    public static final String CHATS_TABLE_NAME = "chats";
    public static final String OUTGOING_MESSAGE_ID = "outgoing_msg_id";
    public static final String INCOMING_MESSAGE_ID = "incoming_msg_id";
    public static final String MESSAGE_TEXT = "msg";
    public static final String SCHEDULE_TIME = "schd_time";
    public static final int DATABASE_VERSION = 6;

    public ChatDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + CHATS_TABLE_NAME +
                        "(id INTEGER PRIMARY KEY, " +
                        INCOMING_MESSAGE_ID + " TEXT, " +
                        OUTGOING_MESSAGE_ID + " TEXT, " +
                        MESSAGE_TEXT + " TEXT, " +
                        SCHEDULE_TIME + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CHATS_TABLE_NAME);
            onCreate(db);
        }
    }


    public boolean insertChat(String incomingMsgId, String outgoingMsgId, String message, Long scheduleTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INCOMING_MESSAGE_ID, incomingMsgId);
        contentValues.put(OUTGOING_MESSAGE_ID, outgoingMsgId);
        contentValues.put(MESSAGE_TEXT, message);
        if (scheduleTime != null) {
            contentValues.put(SCHEDULE_TIME, scheduleTime);
        }
        long result = db.insert(CHATS_TABLE_NAME, null, contentValues);
        return result != -1;
    }


    public Cursor getChats(String incomingId, String outgoingId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM chats WHERE " +
                "((incoming_msg_id = ? AND outgoing_msg_id = ?) " +
                "OR (incoming_msg_id = ? AND outgoing_msg_id = ?)) " +
                "AND (schd_time <= ? OR schd_time IS NULL) " + // Filter by schedule_time
                "ORDER BY id ASC"; // Ensure messages are in order

        String currentTime = String.valueOf(System.currentTimeMillis());
        return db.rawQuery(query, new String[]{incomingId, outgoingId, outgoingId, incomingId, currentTime});
    }



    public Cursor getNames(String outgoingId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT DISTINCT " + INCOMING_MESSAGE_ID +
                        " FROM " + CHATS_TABLE_NAME +
                        " WHERE " + OUTGOING_MESSAGE_ID + " = ?",
                new String[]{outgoingId});
    }


    public Cursor getScheduledMessages(long currentTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + CHATS_TABLE_NAME +
                " WHERE " + SCHEDULE_TIME + " IS NOT NULL AND " + SCHEDULE_TIME + " <= ?";
        return db.rawQuery(query, new String[]{String.valueOf(currentTime)});
    }


    public boolean updateScheduleTime(int messageId, long newScheduleTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_TIME, newScheduleTime);
        int result = db.update(CHATS_TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(messageId)});
        return result > 0;
    }


    public boolean deleteScheduledMessage(int messageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(CHATS_TABLE_NAME, "id = ? AND " + SCHEDULE_TIME + " IS NOT NULL", new String[]{String.valueOf(messageId)});
        return result > 0;
    }
}