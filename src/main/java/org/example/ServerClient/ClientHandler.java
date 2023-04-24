package org.example.ServerClient;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import com.sun.mail.iap.Response;
import org.example.Classes.*;
public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Hotel hotel;

    public enum ResponseType {
        CHECK_ROOM_AVAILABILITY,
        MAKE_RESERVATION
    }

    public ClientHandler(Socket socket, Hotel hotel) {
        this.socket = socket;
        this.hotel = hotel;
    }

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                Object obj = input.readObject();
                if (obj instanceof Request) {
                    Request request = (Request) obj;
                    Response response = processRequest(request);
                    output.writeObject(response);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private Response processRequest(Request request) {
        Response response = null;
        switch (request.getRequestType()) {
            case "CHECK_ROOM_AVAILABILITY":
                Room room = (Room) request.getRequestData();
                Date checkinDate = (Date) request.getRequestData();
                Date checkoutDate = (Date) request.getRequestData();
                boolean isAvailable = hotel.isRoomAvailable(room, checkinDate, checkoutDate);
                String responseType = ResponseType.CHECK_ROOM_AVAILABILITY.name();
                return new Response(responseType, isAvailable);
            case "MAKE_RESERVATION":
                Reservation reservation = (Reservation) request.getRequestData();
                boolean isConfirmed = hotel.confirmReservation(reservation);
                responseType = ResponseType.MAKE_RESERVATION.name();
                return new Response(responseType, isConfirmed);
            default:
                return null;
        }
    }
}

