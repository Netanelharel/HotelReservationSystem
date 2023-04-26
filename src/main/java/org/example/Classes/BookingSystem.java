package org.example.Classes;

import org.example.ServerClient.*;
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

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Server server = new Server(hotel);
        Thread serverThread = new Thread(server);
        serverThread.start();

        Client client1 = new Client("Client 1", hotel);
        Client client2 = new Client("Client 2", hotel);

        client1.checkRoomAvailability(RoomType.SINGLE, LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 3));
        client1.makeReservation(RoomType.SINGLE, LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 3), "John Doe");
        client2.checkRoomAvailability(RoomType.DOUBLE, LocalDate.of(2023, 5, 10), LocalDate.of(2023, 5, 15));
        client2.makeReservation(RoomType.DOUBLE, LocalDate.of(2023, 5, 10), LocalDate.of(2023, 5, 15), "Jane Doe");

        try {
            Thread.sleep(5000); // wait for all requests to be processed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.stopServer();
    }
}

