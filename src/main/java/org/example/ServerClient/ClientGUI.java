//package org.example.ServerClient;
//import org.example.Classes.*;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//public class ClientGUI {
//    private List<Room> rooms;
//    private List<Reservation> reservations;
//    private List<Guest> guests;
//
//    public ClientGUI() {
//        // Initialize the lists of rooms, reservations, and users
//        this.rooms = new ArrayList<>();
//        this.reservations = new ArrayList<>();
////        this.users = new ArrayList<>();
//    }
//
//    public List<Room> getRooms() {
//        // Return a list of all available rooms
//        List<Room> availableRooms = new ArrayList<>();
//        for (Room room : this.rooms) {
//            if (!isRoomReserved(room)) {
//                availableRooms.add(room);
//            }
//        }
//        return availableRooms;
//    }
//
//    public List<Reservation> getReservationsForUser(Guest guest) {
//        // Return a list of reservations for a specific user
//        List<Reservation> userReservations = new ArrayList<>();
//        for (Reservation reservation : this.reservations) {
//            if (reservation.getGuest().equals(guest)) {
//                userReservations.add(reservation);
//            }
//        }
//        return userReservations;
//    }
//
//    public void reserveRoom(Guest guest, Room room, Date checkIn, Date checkOut, int numOfGuests) {
//        // Reserve a room for a user for the specified dates
//        Reservation reservation = new Reservation(guest.getHotelId(), rooms, checkIn, checkOut, numOfGuests);
//        this.reservations.add(reservation);
//    }
//
//    public void cancelReservation(Reservation reservation) {
//        // Cancel a reservation for a user
//        this.reservations.remove(reservation);
//    }
//
//    public void addGuest(Guest guest) {
//        // Add a new user to the system
//        this.guests.add(guest);
//    }
//
//    public boolean isRoomReserved(Room room) {
//        // Check if a room is already reserved for the given dates
//        for (Reservation reservation : this.reservations) {
//            if (reservation.getRoom().equals(room)) {
//                // Check if the reservation overlaps with the requested dates
//                if (reservation.getCheckInDate().before(reservation.getCheckOutDate()) && reservation.getCheckOutDate().after(reservation.getCheckInDate())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public static void main(String[] args) {
//        // Start the hotel reservation system
//        ClientGUI system = new ClientGUI();
//        // Initialize the rooms, users, and reservations
//
//        // Show the user interface
//        // ...
//    }
//}
