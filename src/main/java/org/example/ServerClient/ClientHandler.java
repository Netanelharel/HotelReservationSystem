package org.example.ServerClient;

import org.example.Classes.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A class representing a thread that handles communication with a single client in a client-server application.
 * The class implements the Runnable interface to allow it to run in a separate thread.
 */
public class ClientHandler implements Runnable {
    private Socket socket; // A connected socket for communication with the client
    private ObjectInputStream dataFromClient; //An object input stream for reading data from the client
    private ObjectOutputStream dataToClient; //An object output stream for sending data to the client.
    private int request = 0; //An integer representing the client's request.

    /**
     * Constructs a new HandleAClient object with the given socket.
     *
     * @param socket A connected socket for communication with the client.
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * The main method that runs in a separate thread to handle communication with the client.
     * The method uses a switch statement to determine the client's request and respond accordingly.
     * The method also calls other methods to perform specific tasks, such as showing available lessons,
     * ordering a new lesson or cancelling a lesson.
     */
    @Override
    public void run() {
        try {
            String guestName = "";
            Room room;
            dataToClient = new ObjectOutputStream(socket.getOutputStream());
            dataFromClient = new ObjectInputStream(socket.getInputStream());
            while (true) {
                var x = dataFromClient.readObject();
                request = (int) x;
                switch (request) {
                    case 0: //login
                        System.out.println("\nEntered account login action!\n");
                        guestName = (String) dataFromClient.readObject();
                        if (checkId(guestName)) {
                            dataToClient.writeObject("Guest found!");
                        } else {
                            dataToClient.writeObject("Guest not found! try again.");
                        }
                        break;
                    case 1: //order a new room
                        showAvailableRooms();
                        int roomId = (int) dataFromClient.readObject();
                        int res = orderARoom(guestName, roomId);
                        dataToClient.writeObject(res);
                        break;
                    case 2: //see your reservations
                        List<Reservation> myReservations = showClientReservations(guestName);
                        dataToClient.writeObject(myReservations);
                        break;
                    case 3: //cancel a reservation
                        List<Reservation> clientReservations = showClientReservations(guestName);
                        dataToClient.writeObject(clientReservations);
                        int reservationNum = (int) dataFromClient.readObject();
                        Reservation reservation = BookingSystem.getInstance().getGuestReservations(guestName)
                                .get(reservationNum - 1);
                        cancelRoom(guestName, reservation.getRoom());
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //    public void run() {
//        try {
//            String temp = "";
//            String guestName = "";
//            dataToClient = new ObjectOutputStream(socket.getOutputStream());
//            dataFromClient = new ObjectInputStream(socket.getInputStream());
//            while (true) {
//                var x = dataFromClient.readObject();
//                request = (int) x;
//                switch (request) {
//                    case 0://login
//                        System.out.println("\nEntered account login action!\n");
//                        temp = (String) dataFromClient.readObject();
//                        if (checkId(temp)) {
//                            guestName = temp;
//                            dataToClient.writeObject("Guest found!");
//                        } else {
//                            dataToClient.writeObject("Guest not found! try again.");
//                        }
//                        break;
//                    case 1://order a new room
//                        showAvailableRooms();
//                        int roomID = (int) dataFromClient.readObject();
//                        int res = orderARoom(guestName, roomID);
//                        dataToClient.writeObject(res);
//                        break;
//                    case 2://see your Reservation
//                        String name = (String) dataFromClient.readObject();
//                        List<Reservation> myList = showClientReservations(name);
//                        dataToClient.writeObject(myList);
//                        break;
//                    case 3://cancel a room
////                        int guestId = (int) dataFromClient.readObject();
//                        String guestN= (String) dataFromClient.readObject();
//                        List<Reservation> clientReservation = showClientReservations(guestN);
//                        dataToClient.writeObject(clientReservation);
//                        double lessonNum = (double) dataFromClient.readObject();
//                        Reservation reservation = BookingSystem.getInstance().getGuestReservations()
//                                .get(reservation - 1).getMyLessons().get((int) lessonNum - 1);
//                        cancelRoom(guestN,reservation.getRoom());
//                        //dataToClient.writeObject("cancel success");
//                        break;
//
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * Returns a list of the lessons for a given client.
     *
     * @param guestName The name of the client.
     * @return A list of the client's lessons.
     */
    private List<Reservation> showClientReservations(String guestName) {
        return BookingSystem.getInstance().getGuestReservations(guestName);
    }

    /**
     * Sends a list of available lessons to the client.
     *
     * @throws IOException If an I/O error occurs.
     */
    private void showAvailableRooms() throws IOException {
        Map<String, Integer> availableRoomsByHotel = new HashMap<>();
        List<Hotel> hotels = BookingSystem.getInstance().getHotels();

        for (Hotel hotel : hotels) {
            int availableRooms = 0;
            for (Room room : hotel.getRooms()) {
                if (!room.isBooked()) {
                    availableRooms++;
                }
            }
            availableRoomsByHotel.put(hotel.getName(), availableRooms);
        }
        dataToClient.writeObject(availableRoomsByHotel);
    }

    /**
     * Checks if the given Guest ID exists in the Guest list.
     *
     * @param name The Guest name to check for.
     * @return True if the Guest ID exists, false otherwise.
     */
    public boolean checkId(String name) {
        return BookingSystem.getInstance().isGuestNameExist(name);
    }

    /**
     * Attempts to order a lesson for a Guest. If the lesson is not available,
     * this method waits until it becomes available before attempting to order it again.
     *
     * @param guestName The name of the Guest ordering the room.
     * @param roomId    The ID of the room being ordered.
     * @return 1 if the lesson was successfully ordered, or 0 if an exception occurred.
     */
    public int orderARoom(String guestName, int roomId) {
        Room room;
        try {
            synchronized (BookingSystem.getInstance().getRooms()) {
                while (!BookingSystem.getInstance().getRooms().get(roomId - 1).isBooked()) {
                    BookingSystem.getInstance().getRooms().wait(); // wait for lesson to become available
                }
                BookingSystem.getInstance().getRooms().get(roomId - 1).setBooked(false);
                room = BookingSystem.getInstance().getRooms().get(roomId - 1);
                BookingSystem.getInstance().getRooms().remove(BookingSystem.getInstance().getRooms().get(roomId - 1));
            }
            synchronized (BookingSystem.getInstance().getGuests()) {
                for (Guest guest : BookingSystem.getInstance().getGuests()) {
                    if (guest.getName().equals(guestName)) {
                        guest.setOrderRooms(1, room);
                        break;
                    }
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        } finally {
            synchronized (BookingSystem.getInstance().getRooms()) {
                BookingSystem.getInstance().getRooms().notifyAll(); // notify waiting threads that lesson is available
            }
        }
    }

    /**
     * Cancels a lesson that was previously ordered by a Guest.
     *
     * @param guestName The name of the Guest who ordered the room.
     * @param room      The room object representing the room that was ordered.
     */
    public void cancelRoom(String guestName, Room room) {
        synchronized (BookingSystem.getInstance().getGuests()) {
            for (Guest guest : BookingSystem.getInstance().getGuests()) {
                if (guest.getName().equals(guestName)) {
                    guest.setOrderRooms(0, room);
                    break;
                }
            }
        }
        synchronized (BookingSystem.getInstance().getRooms()) {
            room.setBooked(true);
            BookingSystem.getInstance().getRooms().add(room);
            //DataServer.getInstance().sortLessonList();
        }
    }

}