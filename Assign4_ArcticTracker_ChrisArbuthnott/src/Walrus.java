
public class Walrus extends Animal {
	
	// properties
	private String dentalHealth;
	
	// constructor
	public Walrus(String nm, String gndr, String wght, String dntHlth, GPSTracker trckr) {
		name = nm;
		gender = gndr;
		weight = Double.parseDouble(wght);
		dentalHealth = dntHlth;
		tracker = trckr;
	}
	
	public String getFileName() {
		return "Walrus"+name+".txt";
	}
	
	public String[] getDataLines() {
		String[] lines = new String[9];
		lines[0] = "SPECIES: Walrus";
		lines[1] = "NAME: "+name;
		lines[2] = "GENDER: "+gender;
		lines[3] = "WEIGHT (kg): "+weight;
		lines[4] = "DENTAL HEALTH: "+dentalHealth;
		lines[5] = "KNOWN GPS COORDINATES (latitude, longitude):";
		lines[6] = tracker.getCoords(0);
		lines[7] = tracker.getCoords(1);
		lines[8] = tracker.getCoords(2);
		
		return lines;
	}

}
