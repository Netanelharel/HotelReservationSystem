package org.example.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationHandler {
    private Hotel hotel;
    private List<Reservation> reservationList;
    private boolean isReservationMade;
    private Reservation.ReservationStatus resStat;

    public ReservationHandler(Hotel hotel) {
        this.hotel = hotel;
        this.reservationList = new ArrayList<>();
        this.isReservationMade = false;
    }

    public synchronized boolean makeReservation(RoomType roomType, Date checkinDate, Date checkoutDate, int numOfGuests) {
        while (isReservationMade) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // find the first available room of the specified type
        Room room = null;
        for (Room r : hotel.getRooms()) {
            if (r.getType() == roomType && r.isBooked()) {
                room = r;
                break;
            }
        }
        List<Room> roomList = new ArrayList<>();
        roomList.add(room);

        // check if an available room was found and make the reservation if so
        if (room != null) {
            Reservation reservation = new Reservation(roomType.getId(), hotel.getId(), roomList, checkinDate, checkoutDate, numOfGuests);
            boolean isConfirmed = hotel.confirmReservation(reservation);
            if (isConfirmed) {
                reservationList.add(reservation);
                isReservationMade = true;
                notifyAll();
                return true;
            }
        }

        return false;
    }

    public synchronized boolean cancelReservation(Reservation reservation) {
        while (!isReservationMade) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean isCanceled = hotel.cancelReservation(reservation);
        if (isCanceled) {
            reservationList.remove(reservation);
            isReservationMade = false;
            notifyAll();
            return true;
        }

        return false;
    }

    public synchronized boolean checkRoomAvailability(Room room, Date checkinDate, Date checkoutDate) {
        while (isReservationMade) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean isAvailable = hotel.isRoomAvailable(room, checkinDate, checkoutDate);
        notifyAll();
        return isAvailable;
    }

    public synchronized boolean confirmReservation(Reservation reservation) {
        while (!isReservationMade || reservationList.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        resStat = reservation.getStatus();
        if (resStat != resStat.CONFIRMED) {
            boolean isConfirmed = hotel.confirmReservation(reservation);
            if (isConfirmed) {
                reservation.setStatus(resStat.CONFIRMED);
                notifyAll();
                return true;
            }
        }

        return false;
    }

    public synchronized List<Reservation> getReservations() {
        while (isReservationMade) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return reservationList;
    }
}
