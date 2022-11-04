package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddBooking implements Command {

    private final int customerID;
    private final int flightID;
    //constructor
    public AddBooking(int customerID, int flightID) {
        this.customerID = customerID;
        this.flightID = flightID;
    }
	
    @Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	Customer customer = flightBookingSystem.getCustomerByID(customerID);
    	Flight flight = flightBookingSystem.getFlightByID(flightID);
    	Booking newBook = new Booking(customer,flight, flightBookingSystem.getSystemDate());
    	customer.addBooking(newBook);
    	flight.addPassenger(customer);
    	System.out.println("Customer: " + customer.getName());
    	System.out.println("Flight: " + flight.getDetailsShort());
    	System.out.println("Booked!");
    	
		
	}
}
    