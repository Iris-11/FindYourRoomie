package com.task.miniproject_findyourroomie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class DbConnect extends SQLiteOpenHelper {
    private static String dbName = "FindMyRoomie";
    private static String dbTable = "User";
    private static int dbVersion = 2;


    private static String ID = "id";
    private static String username = "username";
    private static String password = "password";
    private static String email = "email";

    private static String salt = "salt";



    public DbConnect(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + dbTable + "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+username+" TEXT, "+email+" TEXT, "+password+" TEXT, "+salt+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + dbTable);
            onCreate(db);
        }
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        //values.put(ID,user.getId());
        values.put(username,user.getUsername());
        String[] saltAndHash = encrypt(user.getPassword());
        values.put(password,saltAndHash[1]);
        values.put(salt,saltAndHash[0]);
        //values.put(password,user.getPassword());
        values.put(email,user.getEmail());

        db.insert(dbTable,null,values);
    }
    public boolean userExists(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + dbTable + " WHERE " + username + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{uname});

        boolean exists = cursor.moveToFirst(); // if a result exists, the username is taken
        cursor.close();
        db.close();
        return exists;
    }
    public boolean emailExists(String em){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + dbTable + " WHERE " + email + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{em});
        boolean exists = cursor.moveToFirst(); // if a result exists, the email is taken
        cursor.close();
        db.close();
        return exists;
    }

    public User getUserByName(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + dbTable + " WHERE " + username + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{uname});
        if (cursor.moveToFirst()) {
            // Extract data from the cursor
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String salt = cursor.getString(cursor.getColumnIndexOrThrow("salt"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            // Create a User object with the retrieved data
            User user = new User(username, email, password, salt);
            return user;
        }
        return null;
    }

    public String[] encrypt(String plainTxt){
        // returns the hashed salt[0] and password used[1]
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(plainTxt.getBytes(StandardCharsets.UTF_8));
            return new String[]{salt.toString(),hashedPassword.toString()};
        }catch (Exception e){
            System.out.println("Something went wrong with hashing");
            return new String[]{"", ""};
        }
    }

    public String[] encrypt(String plainTxt, String salt){
        // returns the hashed salt[0] and password used[1]
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(plainTxt.getBytes(StandardCharsets.UTF_8));
            return new String[]{salt.toString(),hashedPassword.toString()};
        }catch (Exception e){
            System.out.println("Something went wrong with hashing");
            return new String[]{"", ""};
        }
    }
}
