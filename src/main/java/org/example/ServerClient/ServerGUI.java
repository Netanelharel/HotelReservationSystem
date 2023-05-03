package org.example.ServerClient;

import org.example.Classes.BookingSystem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;


public class ServerGUI extends JFrame {
    // Define the server port number
    private static final int PORT = 8000;

    // Define the server address
    private static final String serverAddress = "localhost";

    // GUI components
    private JTextArea textArea;

    // Networking components
    private ServerSocket serverSocket;
    private BookingSystem bookingSystem;

    /**
     * The main method simply creates a new instance of the ServerGUI class.
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new ServerGUI();
    }

    /**
     * The constructor sets up the GUI, creates a new BookingSystem object,
     * and starts listening for incoming connections.
     *
     * @throws IOException
     */
    public ServerGUI() throws IOException {
        // Set up the GUI
        super("Hotel Reservation Server");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);

        //Create a JTextArea for displaying server messages
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        //Display the server start time
        textArea.append("Hotel Reservation Server started at " + new Date() + '\n');

        // Create the booking system object
        bookingSystem = new BookingSystem();

        // Create a new server socket with the specified port number
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Wait for incoming client connections
        while (true) {
            Socket clientSocket = null;
            try {
                //Accept incoming client connections
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (clientSocket != null) {
                // Display a message indicating a new client connection
                //Display the client number
                textArea.append("New connection from " + clientSocket.getInetAddress().getHostAddress() + " at " + new Date() + '\n');

                // Display a message indicating a new client connection
                Thread task = new Thread(new ClientHandler(clientSocket, bookingSystem));
                task.start();

                //Set the client socket to null to prevent the current connection from being processed again
                clientSocket = null;
            }
        }
    }

    /**
     * function to stop the server from listening to incoming connections
     * @throws IOException
     */
    public void stopServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // handle exception
            }
        }
    }

//        getContentPane().setLayout(new BorderLayout());
//        textArea.setEditable(false);
//        getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
//        setTitle("MultiThreadServer");
//        setSize(500,300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);

//        int clientNum = 1;
//        try {
//            serSocket = new ServerSocket(PORT);
//            textArea.append("MultiThreadServer STARTED at " + new Date() +
//                    ", Port: " + serSocket.getLocalPort() + "\n");
//        }catch (IOException e1){
//            textArea.append("MultiThreadServer could NOT start!");
//            e1.printStackTrace();
//        }
//        while (true){
//            try {
////                socket = serSocket.accept();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//Display the client number
//        textArea.append("Starting thread for client " + clientNum +
//                " at " + new Date() + '\n');
//        //Find the client's host ip address
//        InetAddress address = socket.getInetAddress();
//        textArea.append("Client " + clientNum + "'s IP Address is " +
//                address.getHostAddress() + "\n");
//        //create a new taskfor the connection
//        Thread task = new Thread(new ClientHandler(socket));
//        task.start();
//        clientNum++;
//        }


//    public static void main(String[] args) {
//        try {
//            ServerSocket serverSocket = new ServerSocket(PORT);
//            System.out.println("Server started, waiting for connections...");
//
//            threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//
//                // handle client connection in a new thread
//                Thread t = new Thread(new ClientHandler(clientSocket));
//                t.start();
//                // read/send data to/from client
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    System.out.println("Received message from client: " + inputLine);
//
//                    // send response back to client
//                    out.println("Server received message: " + inputLine);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // define a class to handle each client connection
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BookingSystem bookingSystem;

        public ClientHandler(Socket clientSocket, BookingSystem bookingSystem) {
            this.clientSocket = clientSocket;
            this.bookingSystem = bookingSystem;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String[] tokens = inputLine.split(" ");
                    if (tokens[0].equals("reserve")) {
                        String roomType = tokens[1];
                        int numGuests = Integer.parseInt(tokens[2]);
                        String guestName = tokens[3];
                        int confirmationNumber = bookingSystem.getReservationNumber(guestName, roomType, numGuests);
                        out.println("Reservation confirmed. Confirmation number: " + confirmationNumber);
                    } else if (tokens[0].equals("cancel")) {
                        int confirmationNumber = Integer.parseInt(tokens[1]);
                        boolean canceled = bookingSystem.cancelReservationByOrderNumber(confirmationNumber);
                        if (canceled) {
                            out.println("Reservation canceled.");
                        } else {
                            out.println("Invalid confirmation number.");
                        }
                    } else {
                        out.println("Invalid command.");
                    }
                }

                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e);
            }
        }

    }
}

