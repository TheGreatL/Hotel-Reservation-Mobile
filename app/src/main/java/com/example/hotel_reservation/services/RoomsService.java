package com.example.hotel_reservation.services;

import android.content.Context;

import com.example.hotel_reservation.database.Database;
import com.example.hotel_reservation.models.Room;

import java.util.LinkedList;

public class RoomsService {
    private final Database databaseHelper;
    public RoomsService(Context context) {
        databaseHelper = new Database(context);
    }

    public LinkedList<Room> getRooms(){
     return  null;
    }
    public boolean createRoom(Room room){
        return false;
    }
    public Room getRoomById(int id){
        return null;
    }
    public Room editRoom(Room room){
        return null;
    }
}
