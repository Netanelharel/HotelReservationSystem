package org.example.Classes;

import org.example.ServerClient.*;
import org.example.Classes.RoomType.numOfGuestInRoom;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private static ReservationHandler resHand;


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

    /*public boolean isRoomAvailable(Room room, Date checkinDate, Date checkoutDate) {
        for (Reservation reservation : this.reservations) {
            if (reservation.getRoom() == room) {
                if (checkinDate.compareTo(reservation.getCheckOutDate()) < 0 &&
                        checkoutDate.compareTo(reservation.getCheckInDate()) > 0) {
                    return false;
                }
            }
        }
        return true;
    }*/

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    public List<Reservation> getReservations() {
        return this.reservations;
    }

    public int getReservationNumber(String guestName, String roomType, int numGuests) {
        int reservationNumber = resHand.findReservationNumber(guestName, roomType, numGuests);
        return reservationNumber;
    }
    public boolean cancelReservationByOrderNumber(int orderNumber){
        boolean isCanceled = resHand.cancelReservation(orderNumber);
        if (isCanceled){
            return true;
        }
        return false;
    }
//    public b{}
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("enter the name of the hotel you want to do a reservation: ");
        String hotelName = scan.next();
//        resHand = new ReservationHandler(hotelName);

        Hotel hotel = new Hotel(hotelName);
        resHand = new ReservationHandler(hotel);
//        Server server = new Server();
//        Thread serverThread = new Thread();
//        serverThread.start();

        System.out.println("enter the num of the guest for this reservation: ");
        int numGuests = scan.nextInt();
//        Client client1 = new Client("Client 1", hotel);
//        Client client2 = new Client("Client 2", hotel);
        RoomType roomType;  //numOfGuestInRoom.DOUBLE; // Get the corresponding RoomType from the enum
        Date checkInDate = Date.from(LocalDate.of(2023, 5, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()); // Create a Date object for the check-in date
        Date checkOutDate = Date.from(LocalDate.of(2023, 5, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()); // Create a Date object for the check-out date
        resHand.checkRoomAvailability(numOfGuestInRoom.SINGLE, checkInDate, checkOutDate);
        resHand.makeReservation(RoomType.fromNumOfGuests("SINGLE"), checkInDate, checkOutDate, numGuests);
//        resHand.checkRoomAvailability(numOfGuestInRoom.DOUBLE,checkInDate,checkOutDate);
//        resHand.makeReservation(RoomType.fromNumOfGuests("DOUBLE"),checkInDate,checkOutDate, numGuests);

        try {
            Thread.sleep(5000); // wait for all requests to be processed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        server.stopServer();
    }
}

