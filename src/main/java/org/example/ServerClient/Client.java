package org.example.ServerClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // or enter the IP address of the server here
    private static final int PORT = 8000; // enter the port number of the server here

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
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a message: ");
                String userInput = scanner.nextLine();

                // send input to server
                out.println(userInput);

                // read response from server
                String response = in.readLine();

                // display response to user
                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
