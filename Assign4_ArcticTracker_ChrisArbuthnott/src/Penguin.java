
public class Penguin extends Animal {
	
	// properties
	private String bloodPressure;
	
	// constructor
	public Penguin(String nm, String gndr, String wght, String bP, GPSTracker trckr) {
		name = nm;
		gender = gndr;
		weight = Double.parseDouble(wght);
		bloodPressure = bP;
		tracker = trckr;
	}
	
	public String getFileName() {
		return "Penguin"+name+".txt";
	}
	
	public String[] getDataLines() {
		String[] lines = new String[9];
		lines[0] = "SPECIES: Penguin";
		lines[1] = "NAME: "+name;
		lines[2] = "GENDER: "+gender;
		lines[3] = "WEIGHT (kg): "+weight;
		lines[4] = "BLOOD PRESSURE: "+bloodPressure;
		lines[5] = "KNOWN GPS COORDINATES (latitude, longitude):";
		lines[6] = tracker.getCoords(0);
		lines[7] = tracker.getCoords(1);
		lines[8] = tracker.getCoords(2);
		
		return lines;
	}

}
