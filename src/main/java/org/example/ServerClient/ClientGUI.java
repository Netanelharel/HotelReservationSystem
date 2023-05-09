package org.example.ServerClient;

import org.example.Classes.BookingSystem;
import org.example.Classes.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ClientGUI {

    private JFrame selectRoomFram;
    private JFrame mainFrame;
    private String guestName = "";
    private JTextField inputTF;
    private String state = "username"; //the state for the enter button
    private Client client = new Client();//the connection between the client and server


    public ClientGUI() {
        initialize();
    }

    public void initialize() {
        mainFrame = new JFrame();
        mainFrame.setBounds(70, 70, 60, 60);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));
//        Image icon = Toolkit.getDefaultToolkit().getImage("src/icons/iconDrive.png");
//        mainFrame.setIconImage(icon);

        selectRoomFram = new JFrame();
        selectRoomFram.setBounds(70, 70, 60, 60);

        JLabel jLabel = new JLabel("Hotel Reservation system");
        jLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.getContentPane().add(jLabel, BorderLayout.NORTH);

        JPanel rightPanel = new JPanel();
        mainFrame.getContentPane().add(rightPanel, BorderLayout.EAST);
        rightPanel.setLayout(new GridLayout(0, 1, 2, 0));

        JPanel upPanel = new JPanel();
        mainFrame.getContentPane().add(upPanel, BorderLayout.NORTH);
        upPanel.setLayout(new GridLayout(0, 1, 2, 0));

        JButton newReservationBTN = new JButton("new reservation");
        newReservationBTN.setToolTipText("Click here to create a new reservation");
        newReservationBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
        rightPanel.add(newReservationBTN);

        JButton cancelReservationBTN = new JButton("cancel reservation");
        cancelReservationBTN.setToolTipText("Click here to cancel a reservation");
        cancelReservationBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
        rightPanel.add(cancelReservationBTN);

        JButton reservationsBTN = new JButton("my reservations");
        reservationsBTN.setToolTipText("Click here to view your reservations");
        reservationsBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
        rightPanel.add(reservationsBTN);

        JButton exitBTN = new JButton("exit");
        exitBTN.setToolTipText("Click here to log out of your account or exit the app");
        exitBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
        rightPanel.add(exitBTN);

        JPanel bottomPanel = new JPanel();
        mainFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        inputTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputTF.setToolTipText("write here (numbers only)");
        inputTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c))
                    e.consume();
            }
        });
        bottomPanel.add(inputTF);
        inputTF.setColumns(40);

        JButton enterBTN = new JButton("enter");
        enterBTN.setToolTipText("Click here after typing");
        enterBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
        bottomPanel.add(enterBTN);

        JTextPane accountTP = new JTextPane();
        accountTP.setToolTipText("Connected account number");
        accountTP.setFont(new Font("Tahoma", Font.BOLD, 12));
        accountTP.setBackground(UIManager.getColor("Panel.background"));
//        accountTP.setText("Student ID:");
        accountTP.setEditable(false);
        upPanel.add(accountTP);

        JScrollPane scrollPane = new JScrollPane();
        mainFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.append("Type your ID and press ENTER:"); // default text when first launched program.
        scrollPane.setViewportView(textArea);

        enterBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switch (state) {
                        case "username":
                            client.dataToServer(0);
                            guestName = String.copyValueOf(inputTF.getText().toCharArray());
                            inputTF.setText("");
                            client.dataToServer(guestName);
                            Object found = client.dataFromServer();
                            if (found.equals("Guest found!")) {
                                accountTP.setText("Guest name: " + guestName);
                                state = "option";
                                textArea.setText("");
                                textArea.append("You are now signed in.\nplease choose what you want to do next with the buttons.");
                            } else {
                                guestName = "";
                                textArea.append("\n" + found);
                                state = "username";
                            }
                            break;
                        case "option":
                            textArea.setToolTipText("");
                            textArea.append("\nplease choose what you want to do next with the buttons.");
                            break;
                        case "newReservation":
                            int roomNum = 0;
                            boolean validInput = false;
                            while (!validInput) {
                                try {
                                    roomNum = Integer.parseInt(inputTF.getText());
                                    if (roomNum <= 0 || roomNum > BookingSystem.getInstance().getRooms().size()) {
                                        textArea.append("\ninvalid input, please try again: ");
                                        inputTF.setText("");
                                    } else {
                                        validInput = true;
                                        inputTF.setText("");
                                    }
                                } catch (NumberFormatException exception) {
                                    roomNum = Integer.parseInt(inputTF.getText());
                                }
                            }
                            client.dataToServer(roomNum);
                            int res = (int) client.dataFromServer();
                            if (res != 0) {
                                textArea.setText("");
                                JOptionPane.showConfirmDialog(mainFrame, "The lesson has been successfully booked", "order confirm", JOptionPane.INFORMATION_MESSAGE);
                                textArea.append("\nplease choose what you want to do next with the buttons.");
                            } else {
                                JOptionPane.showMessageDialog(mainFrame, "There is a problem, please try again later.", "error massage", JOptionPane.INFORMATION_MESSAGE);
                            }
                            state = "option";
                            break;
                        case "cancel":
                            int roomNumber = Integer.parseInt(inputTF.getText());
                            inputTF.setText("");
                            client.dataToServer(roomNumber);
                            textArea.setText("");
                            textArea.setText("cancel success");
                            state = "option";
                            break;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        newReservationBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (guestName != null && !state.equals("username")) {
                        client.dataToServer(1);
                        List<Reservation> reservationFromServer = (List<Reservation>) client.dataFromServer();
                        textArea.setText("");
//                        for (int i = 0; true; i++) {
//                            textArea.append("\n" + (i + 1) + ")");
//                            textArea.append(reservationFromServer.get(i).toString());
//                        }
                        if (reservationFromServer.size() == 0){
                            JOptionPane.showMessageDialog(mainFrame,"There are no reservation.","no Reservation", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            textArea.append("\nWrite the number of the lesson you want to order," +
                                    " \nthen click enter: ");
                        }
                        state = "newReservation";
                    }else {
                        textArea.append("\nplease enter your ID first and press enter: ");
                    }
                }catch (Exception exception){
                    textArea.append("\ncannot connect to the hotel's server.");
                }
            }
        });

        reservationsBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(guestName != null && !state.equals("username")){
                        client.dataToServer(2);
                        client.dataToServer(guestName);
                        textArea.setText("");
                        List<Reservation> myReservationsFromServer = (List<Reservation>) client.dataFromServer();
                        if (myReservationsFromServer.size() == 0) {
                            JOptionPane.showMessageDialog(mainFrame, "There are no lessons scheduled.",
                                    "No Lessons", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            int i = 1;
                            for (Reservation reservation : myReservationsFromServer) {
                                textArea.append(i++ + ") " + reservation.toString());
                            }

                        }
                    }
                }catch (Exception exception){
                    textArea.append("\ncannot connect to the hotel's server.");
                }
            }
        });

        cancelReservationBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guestName != null && !state.equals("username")) {
                    client.dataToServer(3);
                    client.dataToServer(guestName);
                    textArea.setText("");
                    List<Reservation> myReservationsFromServer = (List<Reservation>) client.dataFromServer();
                    if (myReservationsFromServer.size() == 0) {
                        JOptionPane.showMessageDialog(mainFrame, "You have no lessons.",
                                "No Lessons", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        int i = 1;
                        for (Reservation reservation : myReservationsFromServer) {
                            textArea.append(i++ + ") " + reservation.toString());
                        }
                        textArea.append("\nWrite the number of the lesson you want to cancel," +
                                " \nthen click enter: ");
                        state = "cancel";
                    }
                } else {
                    textArea.append("\nplease enter your ID first and press enter: ");
                }
            }
        });

        exitBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (guestName != null) {
                        textArea.setText("");
                        textArea.append("Logged out from the account.");
                        textArea.append("\nPlease enter your ID and press ENTER:");
                        accountTP.setText("STUDENT ID:");
                        guestName = "";
                        state = "username";
                    } else {
                        mainFrame.dispose();
                    }
                } catch (Exception e1) {
                    textArea.append("\nCannot connect to the school's server.");
                }
            }
        });
    }

    public static void main(String[] args) {
        ClientGUI window = new ClientGUI();
        window.mainFrame.setVisible(true);
    }
}


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
