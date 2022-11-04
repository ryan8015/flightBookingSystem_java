package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame implements ActionListener {

	private JMenuBar menuBar;
	private JMenu adminMenu;
	private JMenu flightsMenu;
	private JMenu bookingsMenu;
	private JMenu customersMenu;
	final JFrame customers = new JFrame();

	private JMenuItem adminExit;

	private JMenuItem flightsView;
	private JMenuItem flightsAdd;
	private JMenuItem flightsDel;

	private JMenuItem bookingsIssue;
	private JMenuItem bookingsUpdate;
	private JMenuItem bookingsCancel;

	private JMenuItem custView;
	private JMenuItem custAdd;
	private JMenuItem custDel;

	private FlightBookingSystem fbs;

	public MainWindow(FlightBookingSystem fbs) {

		initialize();
		this.fbs = fbs;
	}

	public FlightBookingSystem getFlightBookingSystem() {
		return fbs;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}

		setTitle("Flight Booking Management System");

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		//adding adminMenu menu and menu items
		adminMenu = new JMenu("Admin");
		menuBar.add(adminMenu);

		adminExit = new JMenuItem("Exit");
		adminMenu.add(adminExit);
		adminExit.addActionListener(this);

		// adding Flights menu and menu items
		flightsMenu = new JMenu("Flights");
		menuBar.add(flightsMenu);

		flightsView = new JMenuItem("View");
		flightsAdd = new JMenuItem("Add");
		flightsDel = new JMenuItem("Delete");
		flightsMenu.add(flightsView);
		flightsMenu.add(flightsAdd);
		flightsMenu.add(flightsDel);
		// adding action listener for Flights menu items
		for (int i = 0; i < flightsMenu.getItemCount(); i++) {
			flightsMenu.getItem(i).addActionListener(this);
		}

		// adding Bookings menu and menu items
		bookingsMenu = new JMenu("Bookings");

		bookingsIssue = new JMenuItem("Issue");
		bookingsUpdate = new JMenuItem("Update");
		bookingsCancel = new JMenuItem("Cancel");
		bookingsMenu.add(bookingsIssue);
		bookingsMenu.add(bookingsUpdate);
		bookingsMenu.add(bookingsCancel);
		// adding action listener for Bookings menu items
		for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
			bookingsMenu.getItem(i).addActionListener(this);
		}

		// adding Customers menu and menu items
		customersMenu = new JMenu("Customers");
		menuBar.add(customersMenu);

		custView = new JMenuItem("View");
		custAdd = new JMenuItem("Add");
		custDel = new JMenuItem("Delete");

		customersMenu.add(custView);
		customersMenu.add(custAdd);
		customersMenu.add(custDel);
		// adding action listener for Customers menu items
		custView.addActionListener(this);
		custAdd.addActionListener(this);
		custDel.addActionListener(this);

		setSize(800, 500);

		setVisible(true);
		setAutoRequestFocus(true);
		toFront();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/* Uncomment the following line to not terminate the console app when the window is closed */
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

	}	

	/* Uncomment the following code to run the GUI version directly from the IDE */
	//    public static void main(String[] args) throws IOException, FlightBookingSystemException {
	//        FlightBookingSystem fbs = FlightBookingSystemData.load();
	//        new MainWindow(fbs);			
	//    }



	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == adminExit) {
			try {
				FlightBookingSystemData.store(fbs);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
			}
			System.exit(0);
		} else if (ae.getSource() == flightsView) {
			displayFlights();

		} else if (ae.getSource() == flightsAdd) {
			new AddFlightWindow(this);

		} else if (ae.getSource() == flightsDel) {


		} else if (ae.getSource() == bookingsIssue) {


		} else if (ae.getSource() == bookingsCancel) {


		} else if (ae.getSource() == custView) {
			displayCustomer();

		} else if (ae.getSource() == custAdd) {
			new AddCustomerWindow(this);

		} else if (ae.getSource() == custDel) {

		}
	}


	public void displayFlights() {
		List<Flight> flightsList = fbs.getFlights();
		String[] columns = new String[]{"Flight ID","Flight No", "Origin", "Destination", "Departure Date","Capacity","Show Customers"};

		Object[][] data = new Object[flightsList.size()][6];
		for (int i = 0; i < flightsList.size(); i++) {
			Flight flight = flightsList.get(i);
			data[i][0] = flight.getId();
			data[i][1] = flight.getFlightNumber();
			data[i][2] = flight.getOrigin();
			data[i][3] = flight.getDestination();
			data[i][4] = flight.getDepartureDate();
			data[i][5] = flight.getSeats();

		};
		DefaultTableModel model = new DefaultTableModel(data,columns);
		JTable table = new JTable(model);
		
		AbstractAction popup = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				String string = ((DefaultTableModel)table.getModel()).getValueAt(modelRow, 0).toString();
				int id = Integer.parseInt(string);
				try {
					Flight flight = fbs.getFlightByID(id);
					List<Customer> list = flight.getPassengers();
					String[] columns2 = new String[]{"Name", "Phone","Email"};
					//problem, list is empty
					Object[][] data2 = new Object[list.size()][3];
					for (int i = 0; i < list.size(); i++) {
						Customer customer = list.get(i);
						data2[i][0] = customer.getName();
						data2[i][1] = customer.getPhone();
						data2[i][2] = customer.getEmail();

					};
					JTable table1 = new JTable(data2,columns2);
					JOptionPane.showMessageDialog(null,
							new JScrollPane(table1));
				} catch (FlightBookingSystemException e1) {
					e1.printStackTrace();
				}
				
				
				
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, popup, 6);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(table));
		this.revalidate();
	}	
	public void displayCustomer() {
        List<Customer> customersList = fbs.getCustomers();
        // headers for the table
        String[] columns = new String[]{"ID", "Customer Name", "Phone", "Email","No. Bookings","Booking Details"};

        Object[][] data = new Object[customersList.size()][5];
        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            List<Booking> bookings = customer.getBookings();
            data[i][0] = customer.getID();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
            data[i][4] = bookings.size();

        };
        DefaultTableModel model = new DefaultTableModel(data,columns);
		JTable table = new JTable(model);
		AbstractAction popup = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				String string = ((DefaultTableModel)table.getModel()).getValueAt(modelRow, 0).toString();
				int id = Integer.parseInt(string);
				try {
					Customer customer = fbs.getCustomerByID(id);
					List<Booking> bookings = customer.getBookings();
					String[] columns2 = new String[]{"Customer ID", "Flight ID","Flight Number","Destination","Date of Booking"};
					//problem, list is empty
					Object[][] data2 = new Object[bookings.size()][5];
					for (int i = 0; i < bookings.size(); i++) {
						Booking booking = bookings.get(i);
						Customer custID = booking.getCustomer();
						Flight flightID = booking.getFlight();
						
						data2[i][0] = custID.getID();
						data2[i][1] = flightID.getId();
						data2[i][2] = flightID.getFlightNumber();
						data2[i][3] = flightID.getDestination();
						data2[i][4] = booking.getDate();

					};
					JTable table1 = new JTable(data2,columns2);
					JOptionPane.showMessageDialog(null,
							new JScrollPane(table1));
				} catch (FlightBookingSystemException e1) {
					e1.printStackTrace();
				}
				
				
				
			}
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, popup, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }	
    
    
}
