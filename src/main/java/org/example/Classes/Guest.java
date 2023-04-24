package org.example.Classes;

public class Guest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String notes;

    public Guest(String name, String email, String phoneNumber, String address, String notes) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

