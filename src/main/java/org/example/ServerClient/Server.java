package org.example.ServerClient;

import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 1234; // choose a port number
    private static final String serverAddress = "localhost";
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started, waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

                // handle client connection in a new thread
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // define a class to handle each client connection
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        // add any shared resources between threads here

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run(){
            try {
                Socket socket = new Socket(serverAddress, PORT);
                // code to read from or write to the socket
            } catch (IOException e) {
                System.err.println("Error connecting to server: " + e.getMessage());
                // handle the exception, e.g. by closing the socket and exiting the program
            }

        }

    }
}
