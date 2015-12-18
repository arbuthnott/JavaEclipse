package NoComments;

public class Passenger implements Comparable<Passenger> {
	
	private String name;
	private int row, col;
	private String rowString, colString;
	
	public Passenger(String inputName, int inputRow, int inputCol,
			String inputRowString, String inputColString)
	{
		name = inputName;
		row = inputRow;
		col = inputCol;
		rowString = inputRowString;
		colString = inputColString;
	}
	
	public int compareTo(Passenger p)
	{
		return name.compareTo(p.name);
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public String getRowString()
	{
		return rowString;
	}
	
	public String getColString()
	{
		return colString;
	}
	

}
