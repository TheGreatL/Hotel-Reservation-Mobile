package com.example.hotel_reservation.models;

import android.annotation.SuppressLint;

import java.util.Date;

public class Room {
    private int id;
    private  String name,description;
    private byte[] image;
    private String buildingName;
    private int floorNumber;
    private boolean isAvailable;
    private double price;

    public Room(String name, String description, byte[] image, String buildingName, int floorNumber, boolean isAvailable, double price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.buildingName = buildingName;
        this.floorNumber = floorNumber;
        this.isAvailable = isAvailable;

        this.price = formatPrice(price);
    }



    public Room(int id, String name, String description, byte[] image, String buildingName, int floorNumber, boolean isAvailable, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.buildingName = buildingName;
        this.floorNumber = floorNumber;
        this.isAvailable = isAvailable;
        this.price = formatPrice(price);
    }
    @SuppressLint("DefaultLocale")
    private double formatPrice(double price) {
         String formatPrice = String.format("%.2f",price);
        return Double.parseDouble(formatPrice);
    }
    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
