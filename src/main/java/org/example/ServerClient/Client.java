package org.example.ServerClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * The Client class is responsible for creating a connection to the server and sending/receiving data to/from it.
 */
public class Client {
    private static ObjectOutputStream dataToServer;
    private static ObjectInputStream dataFromServer;
    private Socket socket;

    /**
     * Constructor to create a Client object and initialize the IO streams and socket to connect to the server.
     */
    public Client() {
        try {
            // Create a socket to connect to the server
            socket = new Socket("localhost", 8080);
            // Create an output stream to send data to server
            dataToServer = new ObjectOutputStream(socket.getOutputStream());
            // Create an input stream to receive data from the server
            dataFromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Could not connect to server!");
            ex.printStackTrace();
        }
    }

    /**
     * Method to send data to the server.
     *
     * @param obj The object to send to the server.
     */
    public void dataToServer(Object obj) {
        try {
            dataToServer.writeObject(obj);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to receive data from the server.
     *
     * @return The object received from the server.
     */
    public Object dataFromServer() {
        try {
            return dataFromServer.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
//    private static final String SERVER_ADDRESS = "localhost"; // or enter the IP address of the server here
//    private static final int PORT = 8000; // enter the port number of the server here
//
//    public static void main(String[] args) {
//        try {
//            Socket socket = new Socket(SERVER_ADDRESS, PORT);
//            System.out.println("Connected to server at " + socket.getInetAddress().getHostAddress());
//
//            // set up input and output streams
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//
//            // send/receive data to/from the server
//            while (true) {
//                // read user input
//                Scanner scanner = new Scanner(System.in);
//                System.out.print("Enter a message: ");
//                String[] userInput = new String[]{scanner.nextLine()};
//
//                // send input to server
//                out.println(userInput);
//
//                // read response from server
//                String response = in.readLine();
//
//                // display response to user
//                System.out.println("Server response: " + response);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

