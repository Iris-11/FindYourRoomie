package com.task.miniproject_findyourroomie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="FindYourRoomie";
    private static final int DB_VER=1;
    private static final String TABLE_PREF="Prefer";
    private static final String COL_EMAIL="email";
    private static final String COL_CITY="pref_city";
    private static final String COL_AREA="pref_area";
    private static final String COL_MIN_AGE="pref_min_age";
    private static final String COL_MAX_AGE="pref_max_age";
    private static final String COL_GENDER="pref_gender";
    private static final String COL_JOB="pref_job";
    private static final String COL_SMOKE="pref_smoking";
    private static final String COL_DRINK="pref_drinking";
    private static final String COL_DIET="pref_diet";


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME,null,DB_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPrefTable = "CREATE TABLE "+TABLE_PREF+" ("+
                COL_EMAIL + " TEXT, "+
                COL_CITY + " TEXT, "+
                COL_AREA + " TEXT, "+
                COL_MIN_AGE + " INTEGER, "+
                COL_MAX_AGE + " INTEGER, "+
                COL_GENDER + " TEXT, "+
                COL_JOB + " TEXT, "+
                COL_SMOKE + " TEXT, "+
                COL_DRINK + " TEXT, "+
                COL_DIET + " TEXT )";

        sqLiteDatabase.execSQL(createPrefTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PREF);
        onCreate(sqLiteDatabase);

    }

    public void addpref(String email,String city,String area, int min_age, int max_age, String gender, String job, String smoke, String drink, String diet){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(COL_EMAIL,email);
        values.put(COL_CITY,city);
        values.put(COL_AREA,area);
        values.put(COL_MIN_AGE,min_age);
        values.put(COL_MAX_AGE,max_age);
        values.put(COL_GENDER,gender);
        values.put(COL_JOB,job);
        values.put(COL_SMOKE,smoke);
        values.put(COL_DRINK,drink);
        values.put(COL_DIET,diet);

        db.insert(TABLE_PREF,null,values);
    }



}
