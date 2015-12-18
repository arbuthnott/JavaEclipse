
public abstract class Animal {
	
	// properties
	protected String name;
	protected GPSTracker tracker;
	protected String gender;
	protected double weight;
	
	// abstract methods to be implemented in subclasses
	public abstract String getFileName();
	
	public abstract String[] getDataLines();

}
