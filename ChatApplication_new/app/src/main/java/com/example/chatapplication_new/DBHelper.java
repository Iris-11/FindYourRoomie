package com.example.chatapplication_new;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChatDB.db";
    public static final String CHATS_TABLE_NAME = "chats";
    public static final String OUTGOING_MESSAGE_ID="o_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + CHATS_TABLE_NAME +
                        "(id INTEGER PRIMARY KEY, incoming_msg_id TEXT,"+ OUTGOING_MESSAGE_ID +" TEXT, msg TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertChat(String incomingMsgId, String outgoingMsgId, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("incoming_msg_id", incomingMsgId);
        contentValues.put("outgoing_msg_id", outgoingMsgId);
        contentValues.put("msg", message);
        db.insert(CHATS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getChats(String incomingId, String outgoingId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + CHATS_TABLE_NAME + " WHERE (incoming_msg_id=? AND outgoing_msg_id=?) OR (incoming_msg_id=? AND outgoing_msg_id=?)", new String[]{incomingId, outgoingId, outgoingId, incomingId});
    }
    //method that shows all the messages for the current user (only the uid os pple whi have sent)
    public Cursor getNames(String outgoingId){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT incoming_msg_id FROM " + CHATS_TABLE_NAME + " WHERE outgoing_msg_id=? ", new String[]{ outgoingId});
    }
}
