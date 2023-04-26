package org.example.Classes;

import java.util.Date;
import java.util.List;


public class Reservation {
    private int id;
    private int roomTypeId;
    private int hotelId;
    private Date checkInDate;
    private Date checkOutDate;
    private int numOfGuests;
    private ReservationStatus status;
    private int lastReservation = 0;

    public enum ApprovalStatus {
        CONFIRMED,
        NOT_CONFIRMED
    }

    public enum ReservationStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    private ApprovalStatus isConfirmed = ApprovalStatus.NOT_CONFIRMED;
    private List<Room> rooms;
    private Room room;
    private Guest guest;
    private RoomType roomType;
    private ReservationStatus reservationStatus;

    public Reservation(int roomTypeId, int hotelId, List<Room> rooms, Date checkInDate, Date checkOutDate, int numOfGuests) {
        this.id = lastReservation + 1;
        this.roomTypeId = roomTypeId;
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numOfGuests = numOfGuests;
        this.isConfirmed = ApprovalStatus.NOT_CONFIRMED;
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getRoomTypeId() {
//      return roomTypeId;
//    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
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

//    public String getGuestName(){
//        return Guest.class.getName();
//    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }

    public int getRoomNumber() {
        return room.getRoomNumber();
    }

    public Room getRoom() {
        return this.room;
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

    public synchronized void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
        notifyAll();
    }

    public void cancelReservation() {
        this.isConfirmed = ApprovalStatus.CONFIRMED;
        this.room.setBooked(false); // set the associated room as available
    }
}