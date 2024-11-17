package com.task.miniproject;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewForHostelDb extends SQLiteOpenHelper {
    public static final String DB_NAME = "ReviewForHostelDb";
    public static final int VERSION_db = 1;

    // Table and column definitions
    public static final String TABLE_HOSTELREVIEWS = "hostel_reviews";
    public static final String TABLE_REVIEWlIST = "review_list";
    public static final String COL_NAME = "name";
    public static final String COL_CITY = "city";
    public static final String COL_TEXT = "text";
    public static final String COL_ID = "id";
    public static final String COL_RATING = "rating";

    public ReviewForHostelDb(Context context) {
        super(context, DB_NAME, null, VERSION_db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create hostel_reviews table
        String query1 = "CREATE TABLE " + TABLE_HOSTELREVIEWS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_CITY + " TEXT, " +
                COL_RATING + " TEXT)";

        // Create review_list table with COL_NAME as a foreign key
        String query2 = "CREATE TABLE " + TABLE_REVIEWlIST + " (" +
                COL_NAME + " TEXT NOT NULL, " +
                COL_TEXT + " TEXT, " +
                "FOREIGN KEY (" + COL_NAME + ") REFERENCES " + TABLE_HOSTELREVIEWS + "(" + COL_NAME + "))";

        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWlIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOSTELREVIEWS);
        onCreate(db);
    }

    // Method to return hostel reviews
    public Cursor returnHostelReviews(String hostelName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_TEXT + " FROM " + TABLE_REVIEWlIST + " WHERE " + COL_NAME + " =?";
        return db.rawQuery(query, new String[]{hostelName});
    }

    public void insertReview(SQLiteDatabase db,String hostel_name,String hostel_city,String hostel_text,String hostel_rating){
        //insert query for both the tables
        db= this.getWritableDatabase();
        String query1 = "INSERT OR IGNORE INTO " + TABLE_HOSTELREVIEWS + " (" +
                COL_NAME + ", " + COL_CITY + ", " + COL_RATING + ") VALUES (?, ?, ?)";
        db.execSQL(query1, new String[]{hostel_name, hostel_city, hostel_rating});

        // Insert into review_list table
        String query2 = "INSERT INTO " + TABLE_REVIEWlIST + " (" +
                COL_NAME + ", " + COL_TEXT + ") VALUES (?, ?)";
        db.execSQL(query2, new String[]{hostel_name, hostel_text});


    }
}