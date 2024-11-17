package com.task.miniproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matching {
    private SQLiteDatabase db;

    public Matching(SQLiteDatabase db){
        this.db=db;
    }

    public User getUserDetails(String username){
        String userQuery= "SELECT * FROM User WHERE username = ?";
        Cursor c=db.rawQuery(userQuery,new String[]{username});

        if(c!=null && c.moveToFirst()){
            @SuppressLint("Range") User curUser = new User(
                    c.getString(c.getColumnIndex("email")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("gender")),
                    c.getString(c.getColumnIndex("dob")),
                    c.getString(c.getColumnIndex("contact")),
                    c.getInt(c.getColumnIndex("age")),
                    c.getString(c.getColumnIndex("city")),
                    c.getString(c.getColumnIndex("area")),
                    c.getString(c.getColumnIndex("job")),
                    c.getString(c.getColumnIndex("native")),
                    c.getString(c.getColumnIndex("language")),
                    c.getString(c.getColumnIndex("smoke")),
                    c.getString(c.getColumnIndex("drink")),
                    c.getString(c.getColumnIndex("diet"))
            );
            c.close();
            return curUser;
        }
        return null;
    }

    public List<User> getMatches(User currentuser){
        List<User> availableMatches = new ArrayList<>() ;
        String cur_email=currentuser.getEmail();

        String getQuery= "SELECT * FROM User WHERE status='A' AND email <> ?";
        Cursor cursor=db.rawQuery(getQuery,new String[]{cur_email});

        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range")
                User match=new User(
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("gender")),
                        cursor.getString(cursor.getColumnIndex("dob")),
                        cursor.getString(cursor.getColumnIndex("contact")),
                        cursor.getInt(cursor.getColumnIndex("age")),
                        cursor.getString(cursor.getColumnIndex("city")),
                        cursor.getString(cursor.getColumnIndex("area")),
                        cursor.getString(cursor.getColumnIndex("job")),
                        cursor.getString(cursor.getColumnIndex("native")),
                        cursor.getString(cursor.getColumnIndex("language")),
                        cursor.getString(cursor.getColumnIndex("smoke")),
                        cursor.getString(cursor.getColumnIndex("drink")),
                        cursor.getString(cursor.getColumnIndex("diet"))

                );
                availableMatches.add(match);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return availableMatches;
    }

    public int calculateScore(User currentUser, User user){
        int score=0;
        if(currentUser.getCity().equalsIgnoreCase(user.getCity())){
            score+=15;
        }
        if(currentUser.getArea().equalsIgnoreCase(user.getArea())){
            score+=20;
        }
        if(currentUser.getGender().equalsIgnoreCase(user.getGender())){
            score+=10;
        }
        if(currentUser.getSmoke().equalsIgnoreCase(user.getSmoke())){
            score+=10;
        }
        if(currentUser.getDiet().equalsIgnoreCase(user.getDiet())){
            score+=10;
        }
        if(currentUser.getDrink().equalsIgnoreCase(user.getDrink())){
            score+=10;
        }
        score += 10 - Math.abs(currentUser.getAge() - user.getAge());
        return score;
    }

    public List<Match> getCorrectMatches(User currentUser){
        List<Match> finalMatches = new ArrayList<>();
        List<User> users= getMatches(currentUser);

        if(!users.isEmpty()){
            for(User u: users){
                int s = calculateScore(currentUser,u);
                finalMatches.add(new Match(u,s));
            }

            Collections.sort(finalMatches, (o1,o2)-> o2.getScore()-o1.getScore());
        }
        return finalMatches;
    }
}
