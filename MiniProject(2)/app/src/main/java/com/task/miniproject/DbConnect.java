package com.task.miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

public class DbConnect extends SQLiteOpenHelper {
    private static String dbName = "FindMyRoomie";
    private static String dbTable = "User";
    private static int dbVersion = 7;

    private static String ID = "id";
    private static String username = "username";
    private static String password = "password";
    private static String email = "email";
    private static String col_name = "name";
    private static String col_gender = "gender";
    private static String col_dob = "dob";
    private static String col_contact = "contact";
    private static String col_age= "age";
    private static String col_job = "job";
    private static String col_city = "city";
    private static String col_area = "area";
    private static String col_native_place = "native";
    private static String col_language = "language";
    private static String col_smoke = "smoke";
    private static String col_drink = "drink";
    private static String col_diet = "diet";
    private static String col_status="status";

    private static String salt = "salt";



    public DbConnect(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + dbTable + "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                username+" TEXT, "+
                email+" TEXT, "+
                password+" BLOB, "+
                salt+" BLOB, "+
                col_name+ " TEXT , " +
                col_gender+ " TEXT, "+
                col_dob+ " TEXT, "+
                col_contact + " TEXT , "+
                col_age+ " INTEGER, "+
                col_job+ " TEXT, "+
                col_city+ " TEXT, "+
                col_area+ " TEXT, "+
                col_native_place+ " TEXT, "+
                col_language+ " TEXT, "+
                col_smoke+ " TEXT, "+
                col_drink+ " TEXT, "+
                col_diet+ " TEXT, "+
                col_status+ " TEXT DEFAULT 'A' )";


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
        byte[][] saltAndHash = encrypt(user.getPassword());
        values.put(password,saltAndHash[1]);
        values.put(salt,saltAndHash[0]);
        //values.put(password,user.getPassword());
        values.put(email,user.getEmail());

        db.insert(dbTable,null,values);
    }
    public void addUserDetails(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(col_name,user.getName());
        values.put(col_gender,user.getGender());
        values.put(col_dob,user.getDob());
        values.put(col_contact,user.getContact());
        values.put(col_age,user.getAge());
        values.put(col_job,user.getJob());
        values.put(col_city,user.getCity());
        values.put(col_area,user.getArea());
        values.put(col_native_place,user.getNative_place());
        values.put(col_language,user.getMother_tongue());
        values.put(col_smoke,user.getSmoke());
        values.put(col_drink,user.getDrink());
        values.put(col_diet,user.getDiet());

        db.update(dbTable,values,"email=?",new String[]{user.getEmail()});

    }
    public void updateUserDetails(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(col_contact,user.getContact());
        values.put(col_city,user.getCity());
        values.put(col_area,user.getArea());
        values.put(col_job,user.getJob());
        values.put(col_smoke,user.getSmoke());
        values.put(col_drink,user.getDrink());
        values.put(col_diet,user.getDiet());




        db.update(dbTable,values,"email=?",new String[]{user.getEmail()});

    }

    public boolean userExists(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT username FROM " + dbTable + " WHERE " + username + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{uname});

        boolean exists = cursor.moveToFirst(); // if a result exists, the username is taken
        cursor.close();
        db.close();
        return exists;
    }
    public boolean emailExists(String em){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT email FROM " + dbTable + " WHERE " + email + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{em});
        boolean exists = cursor.moveToFirst(); // if a result exists, the email is taken
        cursor.close();
        db.close();
        return exists;
    }

    public User getUserByName(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT email,salt,password FROM " + dbTable + " WHERE " + username + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{uname});
        if (cursor.moveToFirst()) {
            // Extract data from the cursor
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            byte[] salt = cursor.getBlob(cursor.getColumnIndexOrThrow("salt"));
            byte[] password = cursor.getBlob(cursor.getColumnIndexOrThrow("password"));

            // Create a User object with the retrieved data

            return new User(uname, email, password, salt);
        }
        cursor.close();
        return null;
    }

    public byte[][] encrypt(byte[] plainTxt){
        // returns the hashed salt[0] and password used[1]
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(plainTxt);
            return new byte[][]{salt, hashedPassword};

        }catch (Exception e){
            System.out.println("Something went wrong with hashing");
            return new byte[][]{null, null};
        }
    }

    public boolean passwordMatch(String plainTxt, byte[] salt, byte[] stored){
        // returns the hashed salt[0] and password used[1]
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(plainTxt.getBytes(StandardCharsets.UTF_8));
            return Arrays.equals(hashedPassword, stored);
        }catch (Exception e){
            System.out.println("Something went wrong..");
            return false;
        }
    }


}
