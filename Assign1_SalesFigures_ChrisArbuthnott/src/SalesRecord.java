// Container class for one week's sales data for one salesperson.
public class SalesRecord {
	
	private String salesPersonName;
	private int[] salesFigures;
	
	public SalesRecord(String name, int[] figures)
	{
		salesPersonName = name;
		salesFigures = figures;
	}
	
	public String getName()
	{
		return salesPersonName;
	}
	
	public int getSales(int idx)
	{
		return salesFigures[idx];
	}

}
