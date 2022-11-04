package bcu.cmp5332.bookingsystem.model;
		
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Booking> bookings = new ArrayList<>();
    
    //Constructor
    public Customer(int id, String name, String phone, String email) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    	this.email = email;
    }
    
    //Getters & Setters
    public int getID() {
    	return id;
    }
    
    public void setID(int id) {
    	this.id = id;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getPhone() {
    	return phone;
    }
    
    public void setPhone(String phone) {
    	this.phone = phone;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public List<Booking> getBookings() {
    	return bookings;
    }
    
    public String getDetailsShort() {
        return "Customer #" + id + " - " + name + " - " + phone;
    }

    
    public void addBooking(Booking booking) {
       bookings.add(booking);
    }

    public void cancelBookingForFlight(Flight flight) throws FlightBookingSystemException {
    	bookings.remove(flight);
    }
}
