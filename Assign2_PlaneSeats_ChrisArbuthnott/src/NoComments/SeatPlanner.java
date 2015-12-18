package NoComments;

import java.util.Scanner;
import java.util.Arrays;

public class SeatPlanner {
	
	private int rows, cols;
	private Flight flight;
	private Passenger passengers[];
	Scanner myScanner = new Scanner(System.in);
	
	private final String[] colStrings = {"","Window","Aisle"};
	private final String[] rowStrings = {"","First Class","Economy"};
	
	private final String NAMECHARS = "abcdefghijklmnopqrstuvwxyz'- ";
	
	public void start(int numRows, int numCols)
	{
		rows = numRows;
		cols = numCols;
		flight = new Flight(rows, cols);
		Passenger passenger;
		int passengerCount = 0;
		
		String inputYN, inputName, inputNum;
		int rowPref, colPref;
		int seat[] = new int[2];
		boolean anotherPassenger = true;
		
		System.out.println("Welcome to the SeatPlanner.\n");
		System.out.println(flight.toString());
		
		while(anotherPassenger)
		{	
			inputName = collectName("Please enter the passenger name: ");
			
			inputNum = collect12("Flight class - enter 1 for First Class and 2 for Economy: ");
			rowPref = Integer.parseInt(inputNum);
			
			inputNum = collect12("Seat Preference - enter 1 for Window and 2 for Aisle: ");
			colPref = Integer.parseInt(inputNum);	
			
			seat = getBestSeat(rowPref, colPref);
			
			if (seat[0] == -1)
			{
				System.out.println("Sorry your preferred seats were not available.");
				System.out.println("The next flight departs in 3 hours.");
			}
			else
			{
				flight.fillSeat(seat[0],seat[1], inputName);
				reportSeat(seat, inputName);
				passengerCount++;
			}
			
			if (passengerCount >= rows*cols)
			{
				System.out.println("The plane is now fully booked.");
				anotherPassenger = false;
			}
			else
			{
				inputYN = collectYN("Would you like to book another passenger? (y/n): ");
				if (inputYN.equals("y"))
					System.out.println("\n*** NEXT PASSENGER ***");
				else
					anotherPassenger = false;
			}
		}
		
		passengers = new Passenger[passengerCount];
		int currentIdx = 0;
		for (int row=0; row<rows; row++)
			for (int col=0; col<cols; col++)
			{
				if (flight.seatFull(row, col))
				{
					passenger = flight.getPassenger(row, col);
					passengers[currentIdx] = passenger;
					currentIdx++;
				}
			}
		Arrays.sort(passengers);
		
		System.out.println("\nHere is the final seating chart and passenger list:");
		System.out.println(flight.toString());
		for (int idx=0; idx<passengers.length; idx++)
		{
			passenger = passengers[idx];
			System.out.println(String.format("%s:\trow %d, column %d (%s, %s).",
					passenger.getName(), passenger.getRow()+1, passenger.getCol()+1,
					passenger.getColString(), passenger.getRowString()));
		}
		System.out.println("\nThanks for using SeatPlanner!");
		
	}
	
	private int otherChoice(int num)
	{
		if (num == 1)
			return 2;
		else
			return 1;
	}
	
	private int[] getBestSeat(int rowPref, int colPref)
	{
		String inputYN, outputString;
		int negArray[] = {-1,-1};
		
		if (flight.hasMatch(rowPref, colPref))
			return flight.getMatch(rowPref, colPref);
			
		if (flight.hasMatch(rowPref, otherChoice(colPref)))
		{
			System.out.format("Sorry, there are no %s seats available in %s.\n",
					colStrings[colPref], rowStrings[rowPref]);
			outputString = String.format("%s seats are available.  Is this acceptable? (y/n): ",
					colStrings[otherChoice(colPref)]);
			inputYN = collectYN(outputString);
			if (inputYN.equals("y"))
				return flight.getMatch(rowPref, otherChoice(colPref));
		}
		if (flight.hasMatch(otherChoice(rowPref), colPref))
		{
			System.out.println("Sorry your desired seats are not available.");
			outputString = String.format("%s seats are available in %s.  Is this acceptable? (y/n): ",
					colStrings[colPref], rowStrings[otherChoice(rowPref)]);
			inputYN = collectYN(outputString);
			if (inputYN.equals("y"))
				return flight.getMatch(otherChoice(rowPref), colPref);
			else
				return negArray;
		}
		if (flight.hasMatch(otherChoice(rowPref), otherChoice(colPref)))
		{
			outputString = String.format("The last seats we have are %s seats in %s.  Is this acceptable? (y/n): ",
					colStrings[otherChoice(colPref)], rowStrings[otherChoice(rowPref)]);
			inputYN = collectYN(outputString);
			if (inputYN.equals("y"))
				return flight.getMatch(otherChoice(rowPref), otherChoice(colPref));
		}
		return negArray;
	}
	
	private void reportSeat(int[] seat, String name)
	{
		int pref[] = flight.seatToPref(seat);
		System.out.format("\n%s has been assigned the seat at Row %d, Column %d. (%s, %s)\n",
				name, seat[0]+1, seat[1]+1, colStrings[pref[1]], rowStrings[pref[0]]);
		System.out.println(flight.toString(seat[0],seat[1]));
	}
	
	private String collectYN(String message)
	{
		System.out.print(message);
		String inputYN = myScanner.nextLine().toLowerCase();
		while (!(inputYN.equals("y") || inputYN.equals("n")))
		{
			System.out.println("*** Please enter y or n ***\n");
			System.out.print(message);
			inputYN = myScanner.nextLine();
		}
		return inputYN;
	}
	
	private String collect12(String message)
	{
		System.out.print(message);
		String inputNum = myScanner.nextLine();
		while (!(inputNum.equals("1") || inputNum.equals("2")))
		{
			System.out.println("*** Please enter 1 or 2 ***\n");
			System.out.print(message);
			inputNum = myScanner.nextLine();
		}
		return inputNum;
	}
	
	private String collectName(String message)
	{
		System.out.print(message);
		String inputName = myScanner.nextLine();
		while (!isValidName(inputName.toLowerCase()))
		{
			System.out.println("*** Please enter a name using only ', -, spaces and letters ***\n");
			System.out.print(message);
			inputName = myScanner.nextLine();
		}
		return inputName;
	}
	
	private boolean isValidName(String input)
	{
		if (input.length() == 0)
			return false;
		for(int idx=0; idx<input.length(); idx++)
		{
			if (NAMECHARS.indexOf(input.charAt(idx)) < 0)
				return false;
		}
		return true;
	}
}
