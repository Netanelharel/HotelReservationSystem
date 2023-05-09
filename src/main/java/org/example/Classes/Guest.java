package org.example.Classes;

import java.util.ArrayList;
import java.util.List;

public class Guest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String notes;
    private List<Reservation> reservations;
    private int orderRooms = 0;

    public Guest(String name, String email, String phoneNumber, String address, String notes) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.notes = notes;
        this.reservations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getHotelId() {
        if (reservations.isEmpty()) {
            return -1; // Return -1 to indicate no reservations
        } else {
            return reservations.get(0).getHotelId();
        }
    }

    /**
     * Sets the number of ordered rooms and adds/removes the given room to/from the list of
     * rooms ordered by the guest.
     * @param request An integer that determines whether to add or remove the lesson.
     *                If request == 1, the lesson is added to the list.
     *                If request == -1, the lesson is removed from the list.
     * @param room  The room to add/remove.
     */
    public synchronized void setOrderRooms(int request, Room room) {
        if (request == 1) {
            if (room.isBooked()) {
                System.out.println("Error: Room " + room.getRoomNumber() + " is already bookeed");
                return;
            }
            orderRooms++;
            room.setBooked(true);
            System.out.println("Successfully added a lesson to Room " + room.getRoomNumber() +
                    ". Total number of ordered rooms is now " + orderRooms + ".");
        } else {
            if (orderRooms == 0) {
                System.out.println("Error: No rooms have been ordered yet.");
                return;
            }
            orderRooms--;
            room.setBooked(false);
            System.out.println("Successfully removed a lesson from Room " + room.getRoomNumber() +
                    ". Total number of ordered lessons is now " + orderRooms + ".");
        }
    }
}

