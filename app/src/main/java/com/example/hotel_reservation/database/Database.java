package com.example.hotel_reservation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {


    public Database(@Nullable Context context) {
        super(context, "HotelReservation", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase
        .execSQL("CREATE TABLE IF NOT EXISTS Users(userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstName VARCHAR(100),lastName VARCHAR(100)," +
                "email VARCHAR(100) UNIQUE,password VARCHAR(100));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Rooms(roomId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(100), description TEXT, image BLOB," +
                "floorNumber INTEGER," +
                "buildingName VARCHAR(100)," +
                "isAvailable BOOLEAN);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Reservations(reservationId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "timeIn DATETIME, timeOut DATETIME," +
                "userId INTEGER," +
                "roomId INTEGER," +
                "FOREIGN KEY (userId) REFERENCES Users(userId)," +
                "FOREIGN KEY (roomId) REFERENCES Rooms(roomId))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Rooms");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Reservations");
        onCreate(sqLiteDatabase);
    }

}
