package com.example.hotel_reservation.models;

import java.util.Date;

public class Reservation {
    private int id;
    private final Date timeIn,timeOut;
    private final int userId;
    private final String roomId;
    public Reservation(int id, Date timeIn, Date timeOut, int userId, String roomId) {
        this.id = id;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.userId = userId;
        this.roomId = roomId;
    }

    public Reservation(Date timeIn, Date timeOut, int userId, String roomId) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.userId = userId;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public int getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId;
    }
}
