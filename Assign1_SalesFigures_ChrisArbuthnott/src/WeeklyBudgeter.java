import java.util.regex.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.NumberFormat;

public class WeeklyBudgeter {
	
	private final double[] ITEMPRICES = {239.99,129.75,99.95,350.89};
	// ITEMPRICES stores the prices of all items for sale in order.
	private final double WEEKLYBASEPAY = 200;
	private final double COMMISSIONRATE = 0.09;
	private final String NAMECHARS = "abcdefghijklmnopqrstuvwxyz'- ";
	NumberFormat formatter = NumberFormat.getCurrencyInstance();
	
	// method start.  Run the weekly budgeter.
	public void start()
	{
		Scanner myScanner = new Scanner(System.in);
		System.out.println("Welcome to the weekly budgeter.");
		System.out.println();
		
		// Declare Variables.
		boolean moreRecords = true;
		boolean gotGoodAnswer = false;
		String inputNum, inputYesNo;
		String inputName = "";
		int[] inputSalesFigures = new int[ITEMPRICES.length];
		SalesRecord record;
		ArrayList<SalesRecord> records = new ArrayList<SalesRecord>();
		
		while (moreRecords) // stop collection loop when moreRecords becomes false.
		{
			gotGoodAnswer = false; //switch to true when a valid name is entered.
			while (!gotGoodAnswer)
			{
				System.out.print("Please enter the salesperson's name: ");
				inputName = myScanner.nextLine();
				if (isValidName(inputName.toLowerCase()))
					gotGoodAnswer = true;
				else
					System.out.println(
							"Please enter a name with only -, ', letters, or spaces.");
			}// end while
			
			// in a loop, gather number of items sold for each item.
			for(int idx=0; idx<ITEMPRICES.length; idx++)
			{
				System.out.print("Item "+(idx+1)+"'s sold by "+inputName+": ");
				inputNum = myScanner.nextLine();
				if (isInteger(inputNum))
				{
					inputSalesFigures[idx] = Integer.parseInt(inputNum);
				}
				else
				{
					System.out.println("Please enter an integer.");
					System.out.println("");
					idx --; // reduce idx in loop to re-collect this number.
				}
			}// end for
			
			// Create a SalesRecord object and add it to our ArrayList records.
			record = new SalesRecord(inputName, inputSalesFigures);
			records.add(record);
			
			// print small report for this salesperson.
			printRecordReport(record);
			
			gotGoodAnswer = false;
			while (!gotGoodAnswer) // switch gotGoodAnswer to true after valid y/n input is entered.
			{
				System.out.println("");
				System.out.print("Add records for another salesperson (y/n)?");
				inputYesNo = myScanner.nextLine().toLowerCase();
				switch (inputYesNo)
				{
				case "y": 	gotGoodAnswer = true;
							break;
				case "n": 	gotGoodAnswer = true;
							moreRecords = false; // done entering records
							break;
				default:	System.out.println("Please answer 'y' or 'n'.");
				}
			}// end while
		}// end while
		
		// print final report.
		printFinalReport(records);
		
		System.out.println();
		System.out.println("Closing Weekly Budgeter.");
		myScanner.close();
		
	}// end start method
	
	// Calculate summary data from input records and print to the console.
	private void printFinalReport(ArrayList<SalesRecord> records) {
		
		// variables for item quantities
		int[] itemTotals = new int[ITEMPRICES.length];
		Arrays.fill(itemTotals, 0);
		int topItemIdx = 0;
		int topItemQuantity = 0;
		int totalQuantity = 0;
		
		// variables for sales values
		int topSalesPersonIdx = 0;
		double topSalesValue = 0;
		double totalSalesValue = 0;
		
		// working variables
		SalesRecord currentRecord;
		double salesValue;
		
		// loop through each record, updating itemTotals array, and
		// topSalesValue, totalSalesValue and topSalesPersonIdx.
		for (int recIdx = 0; recIdx < records.size(); recIdx++)
		{
			currentRecord = records.get(recIdx);
			salesValue = 0;
			for (int itemIdx = 0; itemIdx < ITEMPRICES.length; itemIdx++)
			{
				itemTotals[itemIdx] += currentRecord.getSales(itemIdx);
				salesValue += currentRecord.getSales(itemIdx) * ITEMPRICES[itemIdx];
			}
			if (salesValue > topSalesValue)
			{
				topSalesValue = salesValue;
				topSalesPersonIdx = recIdx;
			}
			totalSalesValue += salesValue;
		}// end for
		
		// loop through itemTotals array, updating topItemQuantity,
		// topItemIdx and totalQuantity.
		for (int idx = 0; idx < itemTotals.length; idx++)
		{
			if (itemTotals[idx] > topItemQuantity)
			{
				topItemQuantity = itemTotals[idx];
				topItemIdx = idx;
			}
			totalQuantity += itemTotals[idx];
		}// end for
		
		// Output report to console.
		System.out.println();
		System.out.println("*** FINAL WEEKLY REPORT ***");
		System.out.println("Total items sold:\t" + totalQuantity);
		System.out.println("Total value of sales:\t" + formatter.format(totalSalesValue));
		System.out.println("Average sale value:\t" + formatter.format(
				totalSalesValue / totalQuantity));
		System.out.println();
		
		System.out.println("Top Salesperson: " + records.get(topSalesPersonIdx).getName()
				+ " with " + formatter.format(topSalesValue) + " of sales");
		System.out.println("Top Product: Item " + (topItemIdx+1) + " sold "
				+ topItemQuantity + " units");
		System.out.println("*** END WEEKLY REPORT ***");
	}// end printFinalReport method

	// Calculate summary data from record and print to the console.
	private void printRecordReport(SalesRecord record) {
		String output;
		double amount;
		double total = WEEKLYBASEPAY;
		System.out.println("");
		System.out.println("Weekly pay for "+record.getName()+":");
		System.out.println("Base pay:\t"+formatter.format(WEEKLYBASEPAY)+" base pay");
		for (int idx=0; idx<ITEMPRICES.length; idx++)
		{
			amount = record.getSales(idx) * ITEMPRICES[idx] * COMMISSIONRATE;
			total += amount;
			output = record.getSales(idx)+" Item "+(idx+1)+"'s:\t"
					+ formatter.format(amount)+" commission";
			System.out.println(output);
		}
		System.out.println("Total:\t"+formatter.format(total));
	}// end printRecordReport method

	// return true if input can be interpreted as a non-negative integer.
	private boolean isInteger(String input)
	{
		if (input.length() == 0)
			return false;
		return Pattern.matches("\\d*", input);
	}// end isInteger method
	
	// return true if input is nonempty, using only allowed characters.
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
	}// end isValidName method

}// end WeeklyBudgeter class
