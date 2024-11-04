package com.example.hotelselectioncomplete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConnect extends SQLiteOpenHelper {
    public static final String DB_NAME = "DbConnect";
    public static final int DB_VERSION = 4;
    public final String user="user";//table
    public final String u_id="u_id";
    public final String u_name="u_name";
    public final String u_place="u_place";
    public DbConnect(Context context) {
        // Call the super constructor with database name, version and default factory
        super(context, "DbConnect", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // db=this.getWritableDatabase();
        String query="CREATE TABLE " + user + " (" +
                u_id + " TEXT, " +
                u_name + " TEXT, " +
                u_place + " TEXT  )";
        db.execSQL(query);
        initalizeUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + user);
            onCreate(db);
        }
    }


    public String extractPlaceFromUserId(String u_id_f, SQLiteDatabase db) {
        String query = "SELECT " + u_place + " FROM " + user + " WHERE " + u_id + " = ?";
        Cursor c = db.rawQuery(query, new String[]{u_id_f});
        String place = "";
        if (c.moveToFirst()) {
            place = c.getString(0);  // Assuming one result is returned.
        }
        c.close();
        return place;
    }


    public void initalizeUsers(SQLiteDatabase db){
        // db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("u_id","1");
        values.put("u_name","Ishani");
        values.put("u_place","banglore");
        db.insert(user,null,values);
        values.put("u_id","2");
        values.put("u_name","Ishita");
        values.put("u_place","pune");
        db.insert(user,null,values);
        values.put("u_id","3");
        values.put("u_name","Sanjana");
        values.put("u_place","pune");
        db.insert(user,null,values);
        values.put("u_id","4");
        values.put("u_name","Saniya");
        values.put("u_place","banglore");
        db.insert(user,null,values);
        values.put("u_id","5");
        values.put("u_name","Madhura");
        values.put("u_place","pune");
        db.insert(user,null,values);
    }
}
