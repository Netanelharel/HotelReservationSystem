package org.example.ServerClient;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 1234; // choose a port number
    private static final String serverAddress = "localhost";
    private static final int THREAD_POOL_SIZE = 10; // set the maximum number of threads in the thread pool here
    private static ExecutorService threadPool; // thread pool for handling client requests

    //    private static String user;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started, waiting for connections...");

            threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // handle client connection in a new thread
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                // read/send data to/from client
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received message from client: " + inputLine);

                    // send response back to client
                    out.println("Server received message: " + inputLine);
                }
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

        public void run() {
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
