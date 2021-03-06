package NoComments;

public class Flight {
	
	private int rows, cols;
	private boolean seats[][];
	private String names[][];
	
	private int[] winCols = new int[2];
	private int[] aisleCols = new int[2];
	private int[] firstRows = {0,1};
	private int[] econRows;
	
	private final int WINDOW = 1;
	private final int AISLE = 2;
	private final int FIRST = 1;
	private final int ECON = 2;
	
	private final String[] colStrings = {"","Window","Aisle"};
	private final String[] rowStrings = {"","First Class","Economy"};
	
	public Flight(int numRows, int numCols)
	{
		rows = numRows;
		cols = numCols;
		names = new String[rows][cols];
		seats = new boolean[rows][cols];
		for (int row = 0; row<rows; row++)
			for (int col = 0; col<cols; col++)
				seats[row][col] = false;
		winCols[0] = 0;
		winCols[1] = cols-1;
		aisleCols[0] = rows/2 - 1;
		aisleCols[1] = rows/2;
		econRows = new int[rows-2];
		for (int idx=0; idx<econRows.length; idx++)
			econRows[idx] = idx+2;
		
	}
	
	public boolean seatFull(int rowNum, int colNum)
	{
		return seats[rowNum][colNum];
	} 
	
	public boolean seatEmpty(int rowNum, int colNum)
	{
		return !seats[rowNum][colNum];
	}
	
	public Passenger getPassenger(int rowNum, int colNum)
	{
		Passenger passenger;
		if (seatEmpty(rowNum, colNum))
			return null;
		else
		{
			int seat[] = {rowNum, colNum};
			int prefs[] = seatToPref(seat);
			passenger = new Passenger(names[rowNum][colNum], rowNum, colNum,
					rowStrings[prefs[0]], colStrings[prefs[1]]);
			return passenger;
		}
	}
	
	public void fillSeat(int rowNum, int colNum, String name)
	{
		seats[rowNum][colNum] = true;
		names[rowNum][colNum] = name;
	}
	
	public int[] seatToPref(int[] seat)
	{
		int retArray[] = new int[2];
		if (seat[0]==0 || seat[0]==1)
			retArray[0] = FIRST;
		else
			retArray[0] = ECON;
		if (seat[1]==0 || seat[1]==cols-1)
			retArray[1] = WINDOW;
		else
			retArray[1] = AISLE;
		return retArray;
	}
	
	public int[] getMatch(int rowPref, int colPref)
	{
		int[] colChoices, rowChoices;
		int returnArray[] = {-1,-1};
		if (colPref == WINDOW)
			colChoices = winCols;
		else
			colChoices = aisleCols;
		if (rowPref == FIRST)
			rowChoices = firstRows;
		else
			rowChoices = econRows;
		
		for (int idx1 = 0; idx1 < 2; idx1++)
			for (int idx2 = 0; idx2 < 2; idx2++)
				if (!seats[rowChoices[idx1]][colChoices[idx2]])
				{
					returnArray[0] = rowChoices[idx1];
					returnArray[1] = colChoices[idx2];
					return returnArray;
				}
		return returnArray;
	}
	
	public boolean hasMatch(int rowPref, int colPref)
	{
		return (getMatch(rowPref, colPref)[0] != -1);
	}
	
	public String toString()
	{
		return toString(-1,-1);
	}
	
	public String toString(int rowNum, int colNum)
	{
		String output = "";
		String line;
		for(int row=0; row<rows; row++)
		{
			line = "| ";
			for(int col=0; col<cols; col++)
			{
				if (row==rowNum && col==colNum)
					line += "*";
				else if (seats[row][col])
					line += "O";
				else
					line += "_";
				
				if (col == aisleCols[0])
					line += " ";
			}
			line += " |";
			if (row==0 || row==1)
				line += "  <-- First Class";
			else if (row==rows-1)
				line += "  _: vacant, O: occupied, *: your seat";
			output += (line+"\n");
		}
		return output;
	}
}
