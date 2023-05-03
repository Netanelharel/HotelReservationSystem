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
            Reservation reservation = new Reservation(hotel.getId(), roomList, checkinDate, checkoutDate, numOfGuests);
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

    public synchronized boolean checkRoomAvailability(RoomType.numOfGuestInRoom numOfGuests, Date checkinDate, Date checkoutDate) {
        while (isReservationMade) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Room room = new Room();
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

    public synchronized List<Reservation> getAllReservations() {
        while (isReservationMade) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return reservationList;
    }

    public int findReservationNumber(String guestName, String roomType, int numGuests) {
        int orderNumber = 0;
        List<Reservation> reservations = searchReservations(guestName, roomType, numGuests);
        if (reservations.isEmpty()) {
            return -1;
        }
        orderNumber = reservations.get(0).getId();
        return orderNumber;
    }

    private List<Reservation> searchReservations(String guestName, String roomType, int numGuests) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : getAllReservations()) {
            if (reservation.getGuestName().equals(guestName) &&
                    reservation.getRoomType().equals(roomType) &&
                    reservation.getNumOfGuests() == numGuests) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public synchronized boolean cancelReservation(int orderNumber) {
        Reservation reservation = getReservationByID(orderNumber);
        if(reservation == null)
            return false;
        resStat = reservation.getStatus();
        if(resStat != resStat.CANCELLED){
            reservation.cancelReservation();
            return true;
        }
        return false;
    }

    public Reservation getReservationByID(int orderNumber) {
        for (Reservation res : getAllReservations()) {
            if (res.getId() == orderNumber)
                return res;
        }
        return null;
    }
}
