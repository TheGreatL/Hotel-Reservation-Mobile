package com.example.hotel_reservation.services;

import android.content.Context;

import com.example.hotel_reservation.database.Database;
import com.example.hotel_reservation.models.Reservation;

import java.util.LinkedList;

public class ReservationService {

    private final Database databaseHelper;
    public ReservationService(Context context) {
        databaseHelper = new Database(context);
    }

    public boolean createReservation(Reservation reservation){
        return false;
    }
    public Reservation getReservationByUserId(int id){
        return null;
    }
    public Reservation editReservation(Reservation reservation){
        return  null;
    }
    public LinkedList<Reservation> getReservationsByUserId(int id) {
        return  null;
    }


}
