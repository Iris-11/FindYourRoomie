package com.example.hotelselectioncomplete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HostelReviews.db";
    private static final int DATABASE_VERSION = 16;
    public static final String hostel_name = "hostel_name";
    public static final String hostel_id = "hostel_id";
    public static final String location = "location";
    public static final String contact = "contact";

    public static final int rating=2;

    public static final String Hostels = "hostels"; // Table
    public static final String Reviews = "reviews"; // Table

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HOSTELS_TABLE = "CREATE TABLE " + Hostels + " (" +
                hostel_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                hostel_name + " TEXT NOT NULL, " +
                contact + " TEXT NOT NULL, " +
                location + " TEXT NOT NULL " +
                rating+" INTEGER "+
                ");";

        String CREATE_REVIEWS_TABLE = "CREATE TABLE " + Reviews + " (" +
                "review_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                hostel_id + " INTEGER, " +
                "review_text TEXT, " +
                "FOREIGN KEY (" + hostel_id + ") REFERENCES " + Hostels + "(" + hostel_id + ") ON DELETE CASCADE);";

        db.execSQL(CREATE_HOSTELS_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
        insertSampleHostels(db);
        insertSampleReviews(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Reviews);
        db.execSQL("DROP TABLE IF EXISTS " + Hostels);
        onCreate(db);
    }

    // Method to insert a hostel
    public void insertHostel(String hostelName, String location, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(hostel_name, hostelName);
        values.put(this.location, location);
        values.put(this.contact, contact);
        db.insert(Hostels, null, values);
        db.close();
    }

    // Method to insert a review
    public void insertReview(int hostelId, String reviewText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(hostel_id, hostelId); // Should be an integer
        values.put("review_text", reviewText);
        db.insert(Reviews, null, values);
        db.close();
    }

    // Method to get all hostels
    public Cursor getAllHostels() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + Hostels, null);
    }

    // Method to get reviews for a specific hostel
    public Cursor getReviewsForHostel(int hostelId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + Reviews + " WHERE " + hostel_id + "=?", new String[]{String.valueOf(hostelId)});
    }

    // Method to insert sample hostels
    void insertSampleHostels(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(hostel_name, "Sunshine Hostel");
        values.put(location, "pune");
        values.put(contact, "sunshinehostels.in");
        db.insert(Hostels, null, values);

        values.clear();
        values.put(hostel_name, "Moonlight Hostel");
        values.put(location, "banglore");
        values.put(contact, "moonlighthostels.in");
        db.insert(Hostels, null, values);

        values.clear();
        values.put(hostel_name, "Starview Hostel");
        values.put(location, "pune");
        values.put(contact, "starviewhostels.in");
        db.insert(Hostels, null, values);
    }


    void insertSampleReviews(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(hostel_id, 1);
        values.put("review_text", "Food is good");
        db.insert(Reviews, null, values);

        values.clear();
        values.put(hostel_id, 2);
        values.put("review_text", "Rooms are nice");
        db.insert(Reviews, null, values);

        values.clear();
        values.put(hostel_id, 3);
        values.put("review_text", "Rooms are congested");
        db.insert(Reviews, null, values);
    }


    public Cursor filterHostelFromPlace(String location) {
        SQLiteDatabase db = this.getReadableDatabase(); // Get readable database
        String query = "SELECT * FROM " + Hostels + " WHERE LOWER(" + this.location + ") = LOWER(?)";
        return db.rawQuery(query, new String[]{location.toLowerCase()});

    }

}
