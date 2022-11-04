package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class CancelBooking implements Command {

    private final int customerID;
    private final int flightID;
    //constructor
    public CancelBooking(int customerID, int flightID) {
        this.customerID = customerID;
        this.flightID = flightID;
    }
	
    @Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	Customer customer = flightBookingSystem.getCustomerByID(customerID);
    	Flight flight = flightBookingSystem.getFlightByID(flightID);
    	customer.cancelBookingForFlight(flight);
    	flight.removePassanger(customer);
     	System.out.println("Booking has been deleted for flight to " + flight.getDestination());
	}
}
    	