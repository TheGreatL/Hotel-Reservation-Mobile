package com.example.hotel_reservation.models;

import java.util.Date;

public class Room {
    private int id;
    private  String name,description,image;
    private String buildingName;
    private int floorNumber;
    private boolean isAvailable;

    public Room(int id, String name, String description, String image, String buildingName, int floorNumber, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.buildingName = buildingName;
        this.floorNumber = floorNumber;
        this.isAvailable = isAvailable;
    }

    public Room(String name, String description, String image, String buildingName, int floorNumber, boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.buildingName = buildingName;
        this.floorNumber = floorNumber;
        this.isAvailable = isAvailable;
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

    public String getImage() {
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
