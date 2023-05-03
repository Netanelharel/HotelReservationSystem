package org.example.Classes;

import javax.mail.*;
//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;

import java.util.Properties;
//import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

import static org.example.Classes.Reservation.ApprovalStatus.*;


public class ConfirmationService {

    private static ReservationHandler resHandler;
    public void sendConfirmationEmail(String guestEmail, Reservation reservation) {
        // Implementation to send email to the guest with confirmation details
        // This can be done using any email service provider or library
        // Example using JavaMail library:

        // Set email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Create session with email credentials
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("email@example.com", "password");
            }
        });

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("email@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(guestEmail));
            message.setSubject("Reservation Confirmation");
            message.setText("Dear " + reservation.getGuestName() + ",\n\n" +
                    "Thank you for making a reservation at our hotel. " +
                    "Your reservation details are as follows:\n\n" +
                    "Room Number: " + reservation.getRoomNumber() + "\n" +
                    "Check-in Date: " + reservation.getCheckInDate() + "\n" +
                    "Check-out Date: " + reservation.getCheckOutDate() + "\n\n" +
                    "If you have any questions or concerns, please don't hesitate to contact us.\n\n" +
                    "Best regards,\n" +
                    "The Hotel Team");

            // Send email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void confirmReservation(Reservation reservation){
        resHandler.confirmReservation(reservation);
    }
    /*public void confirmReservation(Reservation reservation) {
        // send confirmation email to guest
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String username = "your-email@gmail.com"; // replace with your email address
            String password = "your-password"; // replace with your email password

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reservation.getGuest().getEmail()));
            message.setSubject("Reservation Confirmation");

            String content = String.format("Dear %s,\n\nYour reservation has been confirmed.\n\nDetails:\nCheck-in date: %s\nCheck-out date: %s\nRoom type: %s\nNumber of guests: %d\n\nThank you for choosing our hotel. We look forward to hosting you.\n\nBest regards,\nThe Hotel Team", reservation.getGuest().getName(), reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getRoomType().getName(), reservation.getNumOfGuests());

            message.setText(content);

            Transport.send(message);
            System.out.println("Reservation confirmation email sent to guest: " + reservation.getGuest().getEmail());

            // update reservation status to confirmed
            reservation.setConfirmed(CONFIRMED);
            System.out.println("Reservation status updated to CONFIRMED");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }*/
}

