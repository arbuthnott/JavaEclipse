
public class SeaLion extends Animal {
	
	// properties
	private int numOfSpots;
	
	// constructor
	public SeaLion(String nm, String gndr, String wght, String nmSpts, GPSTracker trckr) {
		name = nm;
		gender = gndr;
		weight = Double.parseDouble(wght);
		numOfSpots = Integer.parseInt(nmSpts);
		tracker = trckr;
	}
	
	public String getFileName() {
		return "SeaLion"+name+".txt";
	}
	
	public String[] getDataLines() {
		String[] lines = new String[9];
		lines[0] = "SPECIES: Sea Lion";
		lines[1] = "NAME: "+name;
		lines[2] = "GENDER: "+gender;
		lines[3] = "WEIGHT (kg): "+weight;
		lines[4] = "NUMBER OF SPOTS: "+numOfSpots;
		lines[5] = "KNOWN GPS COORDINATES (latitude, longitude):";
		lines[6] = tracker.getCoords(0);
		lines[7] = tracker.getCoords(1);
		lines[8] = tracker.getCoords(2);
		
		return lines;
	}

}
