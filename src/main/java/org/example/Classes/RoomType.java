package org.example.Classes;

import java.util.Arrays;
import java.util.List;

public class RoomType {
    private int id;
    private String name;
    private double price;
    public enum numOfGuestInRoom {
        SINGLE,//(1),
        DOUBLE,//(2)
        TRIPLE,//(3)
        QUAD,//(4)
        FAMILY;//(6)
    }

    private numOfGuestInRoom type;
    public RoomType(int id,double price,numOfGuestInRoom type) {
        this.id = id;
        this.price = price;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

   public numOfGuestInRoom getType() {
        return type;
    }

    public void setType(numOfGuestInRoom type) {
        this.type = type;
    }

    public static RoomType fromNumOfGuests(String numOfGuestsName) {
        numOfGuestInRoom[] guestInRoomList = numOfGuestInRoom.values();
        for (numOfGuestInRoom guestInRoom  : guestInRoomList) {
            if (guestInRoom.name().equalsIgnoreCase(numOfGuestsName)) {
                int id = guestInRoom.ordinal() + 1;
                double price = id * 20;
                return new RoomType(id,price,guestInRoom);
            }
        }
        return null;
    }
//        switch (numOfGuests) {
//            case 1:
//                return new RoomType(1,"SINGLE", 1, 50.0);
//            case 2:
//                return new RoomType(2,"DOUBLE", 2, 75.0);
//            case 3:
//                return new RoomType(3,"TRIPLE", 3, 100.0);
//            case 4:
//                return new RoomType(4,"QUAD", 4, 125.0);
//            case 5:
//            case 6:
//                return new RoomType(6,"FAMILY", 6, 200.0);
//            default:
//                throw new IllegalArgumentException("Invalid number of guests: " + numOfGuests);
//        }

}
