package org.example.Classes;

import java.util.List;
import java.util.Queue;

public class ReservationConfirmationProcess implements Runnable {
    private List<Room> bookedRooms;
    private ConfirmationService confirmationService;
    private Queue<Reservation> reservationQueue;

    public ReservationConfirmationProcess(List<Room> bookedRooms, ConfirmationService confirmationService, Queue<Reservation> reservationQueue) {
        this.bookedRooms = bookedRooms;
        this.confirmationService = confirmationService;
        this.reservationQueue = reservationQueue;
    }
    public Room getRoomById(int roomTypeId) {
        for (Room room : this.bookedRooms) {
            if (room.getRoomTypeId() == roomTypeId) {
                return room;
            }
        }
        return null; // or throw an exception if no room with the given roomTypeId is found
    }

    @Override
    public void run() {
        while (true) {
            synchronized (bookedRooms) {
                while (reservationQueue.isEmpty()) {
                    try {
                        bookedRooms.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Reservation reservation = reservationQueue.poll();
                if (reservation != null) {
                    Room room = getRoomById(reservation.getRoomType().getId());
                    if (room != null && room.isBooked()) {
                        room.setBooked(true);
                        bookedRooms.add(room);
                        confirmationService.confirmReservation(reservation);
                        System.out.println("Reservation confirmed for room " + room.getRoomNumber() + ".");
                    }
                }
            }
        }
    }

//    private Room getRoomById(int roomTypeId) {
//        // search for a room with the given roomTypeId and return it
//    }
}
