package org.example.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public BookingSystem() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public boolean isRoomAvailable(Room room, Date checkinDate, Date checkoutDate) {
        for (Reservation reservation : this.reservations) {
            if (reservation.getRoom() == room) {
                if (checkinDate.compareTo(reservation.getCheckOutDate()) < 0 &&
                        checkoutDate.compareTo(reservation.getCheckInDate()) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    public List<Reservation> getReservations() {
        return this.reservations;
    }
}

