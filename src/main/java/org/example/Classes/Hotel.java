package org.example.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Hotel {
    private int id;
    private String name;
    private String location;
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean isRoomAvailable(Room room, Date checkinDate, Date checkoutDate) {
        // Check if the room is available for the entire duration of the requested stay
        for (Reservation reservation : this.reservations) {
            // If the room has already been reserved for any part of the requested stay, it is not available
            if (reservation.getRoom() == room) {
                if (checkinDate.compareTo(reservation.getCheckOutDate()) < 0 &&
                        checkoutDate.compareTo(reservation.getCheckInDate()) > 0) {
                    return false;
                }
            }
        }
        // If the room is not reserved for any part of the requested stay, it is available
        return true;
    }

    public boolean confirmReservation(Reservation reservation) {
        if (reservation.isConfirmed() == Reservation.ApprovalStatus.CONFIRMED) {
            return true; // reservation already confirmed
        }

        if (isRoomAvailable(reservation.getRoom(), reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            reservation.setConfirmed(Reservation.ApprovalStatus.CONFIRMED);
            return true;
        } else {
            return false; // room not available
        }
    }

    public boolean cancelReservation(Reservation reservation) {
        if (reservation != null && reservation.getRoom().isBooked() == false) {
            reservation.cancelReservation();
            return true;
        } else {
            return false;
        }
    }

}
