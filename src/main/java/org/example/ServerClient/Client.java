package org.example.ServerClient;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // or enter the IP address of the server here
    private static final int PORT = 1234; // enter the port number of the server here

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connected to server at " + socket.getInetAddress().getHostAddress());

            // set up input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // send/receive data to/from the server
            while (true) {
                // read user input
                // send input to server
                // read response from server
                // display response to user
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
