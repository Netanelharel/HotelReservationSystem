package org.example.Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private int roomNumber;
    private int roomTypeId;
    private boolean isBooked;
    private RoomType type;
    List<Hotel> hotels = new ArrayList<>();
    private Hotel hotel;
    public Room() {
    }

    public Room(int id, int roomNumber, RoomType type) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.isBooked = false;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomTypeId() {
        return type.getId();
    }
    public RoomType getType() {
        return this.type;
    }
    public double getPrice(){
        return this.type.getPrice();
    }
    public void setType(RoomType type) {
        this.type = type;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Hotel getHotel() {
//        for (Hotel hotel : hotels) {
//            if (hotel.getRooms().contains(room)) {
//                return hotel;
//            }
//        }
//        return null;
        return this.hotel;
    }

//    public synchronized boolean checkAvailability(LocalDate checkinDate, LocalDate checkoutDate) {
//        while (isBooked) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        if (checkinDate.isBefore(LocalDate.now()) || checkoutDate.isBefore(checkinDate)) {
//            return false;
//        }
//        return isBooked;
//    }
    public synchronized void bookRoom() {
        isBooked = false;
        notifyAll();
    }

    public synchronized void checkoutRoom() {
        isBooked = true;
        notifyAll();
    }
}
