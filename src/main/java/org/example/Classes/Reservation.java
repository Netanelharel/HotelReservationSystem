package org.example.Classes;

import java.util.Date;
import java.util.List;


public class Reservation {
    private int id;
    //    private int roomTypeId;
    private int hotelId;
    private Date checkInDate;
    private Date checkOutDate;
    private int numOfGuests;
    private int lastReservation = 0;

    public enum ApprovalStatus {
        CONFIRMED,
        NOT_CONFIRMED
    }

    public enum ReservationStatus {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    private ApprovalStatus isConfirmed = ApprovalStatus.NOT_CONFIRMED;
    private ReservationStatus status = ReservationStatus.COMPLETED;
    private List<Room> rooms;
    private Room room;
    private Guest guest;
    private Hotel hotel;
    private RoomType roomType;
    private ReservationStatus reservationStatus;

    public Reservation(int hotelId, List<Room> rooms, Date checkInDate, Date checkOutDate, int numOfGuests) {
        this.id = lastReservation + 1;
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numOfGuests = numOfGuests;
        this.isConfirmed = ApprovalStatus.NOT_CONFIRMED;
        this.status = ReservationStatus.PENDING;
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumOfGuests() {
        return numOfGuests;
    }

    public void setNumOfGuests(int numOfGuests) {
        this.numOfGuests = numOfGuests;
    }

    public ApprovalStatus isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(ApprovalStatus confirmed) {
        isConfirmed = ApprovalStatus.CONFIRMED;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getGuestName() {
        return guest.getName();
    }

    public int getRoomNumber() {
        return room.getRoomNumber();
    }

    public Room getRoom() {
        return this.room;
    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCELLED;
        this.room.setBooked(false); // set the associated room as available
    }
    public synchronized void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
        notifyAll();
    }
//    public synchronized boolean confirmReservation() {
//        while (reservationStatus != ReservationStatus.CONFIRMED) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return true;
//    }
}