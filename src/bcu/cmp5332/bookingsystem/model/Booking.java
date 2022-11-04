package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

public class Booking {
    
    private Customer customer;
    private Flight flight;
    private LocalDate date; 
 
    

    public Booking(Customer customer, Flight flight, LocalDate date) {
       this.customer = customer;
       this.flight = flight;
       this.date = date;

    }
    
    public Customer getCustomer() {
    	return customer; 
    }
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
    
    public Flight getFlight() {
    	return flight;
    }
    
    public void setFlight(Flight flight) {
    	this.flight = flight;
    }

    public LocalDate getDate() {
    	return date;
    }
}

