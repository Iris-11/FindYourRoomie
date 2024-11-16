package com.example.hotelselectioncomplete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ChatDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="chat_db";
    public static final int VERSION=1;
    public static final String TABLE_MESSAGES="messages";
    public static final String COL_ID="id";
    public static final String COL_MESSAGE="message";
    public static final String COL_SENDER="sender";
    public static final String COL_TIMESTAMP="timestamp";

    public ChatDb( Context context) {
        super(context,"chat_db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_MESSAGES + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MESSAGE + " TEXT, " +
                COL_SENDER + " TEXT, " +
                COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addMessage(String message, String sender, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MESSAGE, message);
        values.put(COL_SENDER, sender);
        values.put(COL_TIMESTAMP, timestamp);
        db.insert(TABLE_MESSAGES, null, values);
        db.close();
    }
    public Cursor getAllMessages() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MESSAGES, null);
    }
}
