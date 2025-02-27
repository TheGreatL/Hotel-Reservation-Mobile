package com.example.hotel_reservation.services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hotel_reservation.database.Database;
import com.example.hotel_reservation.models.Room;

import java.util.ArrayList;
import java.util.LinkedList;

public class RoomsService {
    private final    SQLiteDatabase readable,writable;
    public RoomsService(Context context) {
        Database databaseHelper = new Database(context);
            readable = databaseHelper.getReadableDatabase();
            writable = databaseHelper.getWritableDatabase();

    }

    public ArrayList<Room> getRooms(){
        ArrayList<Room> roomsList = new ArrayList<>();

       Cursor cursor = readable.rawQuery("SELECT * FROM Rooms",null);

       while(cursor.moveToNext()){
          roomsList.add(initializeRoom(cursor));
       }
        cursor.close();
        return roomsList;
    }
    public boolean createRoom(Room room){

        ContentValues values  = new ContentValues();
        values.put("name",room.getName());
        values.put("description",room.getDescription());
        values.put("image",room.getImage());
        values.put("buildingName",room.getBuildingName());
        values.put("floorNumber",room.getFloorNumber());
        values.put("isAvailable",room.isAvailable());
        values.put("price",room.getPrice());
        return writable.insert("Rooms",null,values)>1;
    }

    public Room getRoomById(int id) throws Error {

        Cursor cursor = readable.rawQuery("SELECT * FROM Rooms WHERE roomId =?",new String[]{Integer.toString(id)});
        if(cursor.getColumnCount() ==0) throw new Error("Room Not Found");
        cursor.moveToNext();
        Room room = initializeRoom(cursor);
        cursor.close();
        return room;
    }
    public Room editRoom(Room room) throws Error{

        Cursor cursor = readable.rawQuery("SELECT * FROM Rooms WHERE roomId =?",new String[]{Integer.toString(room.getId())});
        if(cursor.getColumnCount() ==0) throw new Error("Room Not Found");


        ContentValues newValues = new ContentValues();
        newValues.put("name",room.getName());
        newValues.put("description",room.getDescription());
        newValues.put("image",room.getImage());
        newValues.put("buildingName",room.getBuildingName());
        newValues.put("floorNumber",room.getFloorNumber());
        newValues.put("isAvailable",room.isAvailable());
        newValues.put("price",room.getPrice());

        if(writable.update("Rooms",newValues,"roomId =?",new String[]{Integer.toString(room.getId())}) >0){
           return getRoomById(room.getId());
        }


        return null;
    }
    public boolean deleteRoom(int roomId){
        long  rowsAffected = writable.delete("Rooms","roomId = ?",new String[]{Integer.toString(roomId)});
        return rowsAffected >0;
    }

    @SuppressLint("Range")
    private Room initializeRoom(Cursor cursor){
        return new Room(cursor.getInt(cursor.getColumnIndex("roomId")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("description")),
                cursor.getBlob(cursor.getColumnIndex("image")),
                cursor.getString(cursor.getColumnIndex("buildingName")),
                cursor.getInt(cursor.getColumnIndex("floorNumber")),
                cursor.getInt(cursor.getColumnIndex("isAvailable"))==1,
                cursor.getFloat(cursor.getColumnIndex("price")));
    }
}
