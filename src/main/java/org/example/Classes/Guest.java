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


}

