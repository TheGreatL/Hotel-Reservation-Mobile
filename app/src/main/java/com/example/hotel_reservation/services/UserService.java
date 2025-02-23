package com.example.hotel_reservation.services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hotel_reservation.models.User;
import com.example.hotel_reservation.database.Database;

import java.util.LinkedList;

public class UserService {
   private final Database databaseHelper;
    public UserService(Context context) {
        databaseHelper = new Database(context);
    }

    public boolean createUser(User newUser) throws Exception {
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM Users WHERE email =?"
                ,new String[]{newUser.getEmail()});
        long userCount = cursor.getColumnCount();

        if(userCount >0){
            cursor.close();
            throw new Exception("Your email is already Taken");
        }
        cursor.close();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("firstName",newUser.getFirstName());
        values.put("getLastName",newUser.getLastName());
        values.put("email",newUser.getEmail());
        values.put("password",newUser.getPassword());
        long numberOfInsert = database.insert("Users",null,values);
        return  numberOfInsert> 0;
    }

    public LinkedList<User> getUsers(){
        LinkedList<User> usersList = new LinkedList<>();
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Users",null);

        while (cursor.moveToNext()){
            User user = intializeUser(cursor);
            usersList.push(user);
        }
        cursor.close();
        return usersList;
    }

    public User getUserById(int userId) throws Exception {
        User user;
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE userId =?",new String[]{Integer.toString(userId)});
        long userCount = cursor.getColumnCount();
        if(userCount <=0){
            cursor.close();
            throw new Exception("User not found");
        }
        cursor.moveToNext();
        user =intializeUser(cursor);
        cursor.close();
        return user;
    }
    @SuppressLint("Range")
    private User intializeUser(Cursor cursor){
        return   new User(
                cursor.getInt(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex("firstName")),
                cursor.getString(cursor.getColumnIndex("lastName")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("password"))
        );
    }

    public User userLogin(String email,String password) throws Exception {
        User user;
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE email =? AND password =?",new String[]{email,password});
        long userCount = cursor.getColumnCount();

        if(userCount <=0){
            cursor.close();
            throw new Exception("User not found");
        }
        cursor.moveToNext();
        user = intializeUser(cursor);
        cursor.close();
        return user;
    }
}
