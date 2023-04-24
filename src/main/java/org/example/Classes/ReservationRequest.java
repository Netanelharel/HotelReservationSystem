package org.example.Classes;
import java.util.Date;
public class ReservationRequest {
        private int roomNumber;
        private Date checkInDate;
        private Date checkOutDate;
        private String guestName;

        public ReservationRequest(int roomNumber, Date checkInDate, Date checkOutDate, String guestName) {
            this.roomNumber = roomNumber;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.guestName = guestName;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public Date getCheckInDate() {
            return checkInDate;
        }

        public Date getCheckOutDate() {
            return checkOutDate;
        }

        public String getGuestName() {
            return guestName;
        }

        @Override
        public String toString() {
            return "ReservationRequest{" +
                    "roomNumber=" + roomNumber +
                    ", checkInDate=" + checkInDate +
                    ", checkOutDate=" + checkOutDate +
                    ", guestName='" + guestName + '\'' +
                    '}';
        }
}
