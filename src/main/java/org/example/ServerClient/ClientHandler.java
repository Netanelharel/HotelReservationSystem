//package org.example.ServerClient;
//
//import com.stripe.model.Account;
//
//import javax.swing.*;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//
////import java.net.Socket;
////import java.io.IOException;
////import java.io.ObjectInputStream;
////import java.io.ObjectOutputStream;
////import java.util.Date;
////
////import com.sun.mail.iap.Response;
////import org.example.Classes.*;
//public class ClientHandler extends Thread {
//    private Socket socket;
//    private DataInputStream dataFromClient;
//    private DataOutputStream dataToClient;
//    private int request = 0;
//
//    /* request is a variable to indicate what the client wants to do.
//     * client sends a number:
//     * 0 - account number set - default
//     * 1 - withdraw
//     * 2 - deposit
//     * 3 - balance check
//     */
//
//    public ClientHandler(Socket socket) {
//        this.socket = socket;
//    }
//
//    public void run() {
//        try {
//            dataToClient = new DataOutputStream(socket.getOutputStream());
//            dataFromClient = new DataOutputStream(socket.getInputStream());
//
//            double accountID = 0;
//            double temp = 0;
//            int w = 0;
//
//            /* Always serve client - receiving the number to indicate which
//            action to do as stated above (below the request variable).
//            * the *first* number the server receives from the client is what
//            action to perform, then moves to a switch-case for each action.
//            * most actions require an amount or a number from the client.
//            */
//            while (true) {
//                request = (int) dataFromClient.readDouble();
//                switch (request) {
//                    case 0:
//                        System.out.println("\nEntered account login action!\n");
//
//                        temp = dataFromClient.readDouble();
//                        if (CheckID(temp)) {
//                            accountID = temp;
//                            dataToClient.writeUTF("Account found!");
//                        } else {
//                            dataToClient.writeUTF("Account not found!  try again.");
//                        }
//                        break;
//
//                    case 1: //withdraw action
//                        System.out.println("\nEntered withdraw action\n");
//                        temp = dataFromClient.readDouble();
//                        w = withdraw((int) accountID, temp);
//                        if (w == 1) {
//                            dataToClient.writeUTF("Wirhdrawn " + temp + "successfuly!");
//                        } else if (w == -1)  // Not enough cash
//
//                        {
//                            dataToClient.writeUTF("Not enough cash in the ATM, do you want to wait 30 seconds for a deposit? " +
//                                    "\nType 1 for YES or 0 for NO and press enter");
//                            temp = dataFromClient.readDouble();
//                            if (temp == 1) {
//                                synchronized (Server.cash) {
//                                    Server.cash.wait(30000);// waits
//                                }
//                                dataToClient.writeUTF("Done waiting, please check your balance and try to withdraw. ");
//                            } else {
//                                dataToClient.writeUTF("Not waiting for deposit");
//                            }
//                        } else if (w == 2) // Not enough money in the account
//                        {
//                            dataToClient.writeUTF("Not enough cash in your account, do you want to wait 30 seconds for a deposit? " +
//                                    "\nType 1 for YES or 0 for NO and press enter");
//                            temp = dataFromClient.readDouble();
//                            if (temp == 1) {
//                                synchronized (Server.accounts) {
//                                    Server.accounts.wait(30000); // waits
//                                }
//                                dataToClient.writeUTF("Done waiting, " +
//                                        "please check your balance and try to withdraw.");
//                            } else {
//                                dataToClient.writeUTF("Not waiting for deposit. ");
//                            }
//                        } else //if any error occurs
//                        {
//                            dataToClient.writeUTF("Cannot withdraw, " +
//                                    "please check your balance and try again!");
//                        }
//                        break;
//
//                    case 2: // Deposit action
//                        System.out.println("\nEntered deposit action!\n");
//                        temp = dataFromClient.readDouble();
//                        if (deposit((int) accountID, temp)) {
//                            dataToClient.writeUTF("Deposited " + temp + "successfully !");
//                        } else {
//                            dataToClient.writeUTF("Cannot deposit, please" +
//                                    "check your balance and try again !");
//                        }
//                        break;
//
//                    case 3: //Balance action
//                        System.out.println("Entered balance!");
//                        dataToClient.writeDouble(balance((int) accountID));
//                        break;
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Disconnected");
//        } catch (Exception ex) {
////            ex.printStackTrace();
//        }
//    }
//
//    /*
//     * Functions used to perform different actions
//     * (-1 after id because it starts from 0 in the arraylist but saved as 1 in the id)
//     */
//    // checkID function returns true if id is found within the accounts arraylist, or false if not .
//
//    private boolean checkID(double id) {
//        for (Account a : Server.accounts) {
//            if (a.getId() == (int) id)
//                return true;
//        }
//        return false;
//    }
//
//    // deposit function returns true when deposited the amount of money into account with given id.
//    private boolean deposit(int id, double amount) {
//        try {
//            synchronized (Server.cash) {
//                synchronized (Server.accounts) {
//                    Server.accounts.get(id -
//                            1).getBalance().addCash(amount);
//                    Server.cash.addCash(amount);
//                    Thread.sleep(2000); // To make the action take time
//                    Server.accounts.notifyAll();
//                    Server.cash.notifyAll();
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }
//        return false;
//    }
//
//    // withdraw function returns true if was able to withdraw given amount from account with given id, or false if not.
//    private int withdraw(int id, double amount) {
//        try {
//            synchronized (Server.cash) {
//                if (Server.cash.getCash() - amount >= 0) {
//                    synchronized (Server.accounts) {
//                        if (Server.accounts.get(id1).getBalance().getCash() - amount >= 0) {
//                            Server.accounts.get(id -
//                                    1).getBalance().remCash(amount);
//                            Server.cash.remCash(amount);
//                            Thread.sleep(2000); // To make the action
//                            take time
//                            return 1;
//                        } else {
//                            return -2;
//                        }
//                    }
//                } else {
//                    return -1;
//                }
//            }
//        } catch (Exception e) {
////e.printStackTrace();
//        }
//        return 0;
//    }
//
//    // balance function returns a double which is the balance of the given account id.
//    private double balance(int id) {
//        try {
//            synchronized (Server.accounts) {
//                double balance = Server.accounts.get(id -
//                        1).getBalance().getCash();
//                Thread.sleep(4000); // To make the action take time
//                return balance;
//            }
//        } catch (Exception e) {
////e.printStackTrace();
//        }
//        return -1;
//    }
//}
//
//
////
////    private Socket socket;
////    private ObjectInputStream input;
////    private ObjectOutputStream output;
////    private Hotel hotel;
////
////    public enum ResponseType {
////        CHECK_ROOM_AVAILABILITY,
////        MAKE_RESERVATION
////    }
////
////    public ClientHandler(Socket socket, Hotel hotel) {
////        this.socket = socket;
////        this.hotel = hotel;
////    }
////
////    @Override
////    public void run() {
////        try {
////            input = new ObjectInputStream(socket.getInputStream());
////            output = new ObjectOutputStream(socket.getOutputStream());
////
////            while (true) {
////                Object obj = input.readObject();
////                if (obj instanceof Request) {
////                    Request request = (Request) obj;
////                    Response response = processRequest(request);
////                    output.writeObject(response);
////                }
////            }
////        } catch (IOException | ClassNotFoundException e) {
////            System.out.println("Error: " + e.getMessage());
////        } finally {
////            try {
////                input.close();
////                output.close();
////                socket.close();
////            } catch (IOException e) {
////                System.out.println("Error: " + e.getMessage());
////            }
////        }
////    }
////
////    private Response processRequest(Request request) {
////        Response response = null;
////        switch (request.getRequestType()) {
////            case "CHECK_ROOM_AVAILABILITY":
////                Room room = (Room) request.getRequestData();
////                Date checkinDate = (Date) request.getRequestData();
////                Date checkoutDate = (Date) request.getRequestData();
////                boolean isAvailable = hotel.isRoomAvailable(room, checkinDate, checkoutDate);
////                String responseType = ResponseType.CHECK_ROOM_AVAILABILITY.name();
////                return new Response(responseType, isAvailable);
////            case "MAKE_RESERVATION":
////                Reservation reservation = (Reservation) request.getRequestData();
////                boolean isConfirmed = hotel.confirmReservation(reservation);
////                responseType = ResponseType.MAKE_RESERVATION.name();
////                return new Response(responseType, isConfirmed);
////            default:
////                return null;
////        }
////    }
////}
////
