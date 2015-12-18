import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArcticDataManager {
	
	// properties
	private BufferedReader reader;
	private BufferedWriter writer;
	
	// methods to view / display files
	
	// return a list of files saved using this application.
	public String[] getAnimalFileNames() {
		File dir = new File("animals");
		return dir.list(new FilenameFilter() { 
	         public boolean accept(File dir, String filename){
	        	 return (filename.startsWith("Penguin") ||
	        			 filename.startsWith("SeaLion") ||
	        			 filename.startsWith("Walrus"));
	         }
		} );
	}
	
	// return the contents of input filename as a String[]
	public String[] getLinesFromFile(String filename) {
		String[] lines = new String[9];
		try {
			reader = new BufferedReader(new FileReader("animals/"+filename));
			for (int idx=0; idx<lines.length; idx++)
				lines[idx] = reader.readLine();
		} catch (IOException e) {
			System.out.println("Error reading file: "+e.getMessage());
			e.printStackTrace();
		}
		return lines;
	}
	
	// methods to save Animal Data to File
 	public String savePenguin(String name, String gender, String weight, String bloodPressure,
			String gpsa, String gpsb, String gpsc) {
		String initialTest = initialValidation(name, gender, weight, gpsa, gpsb, gpsc);
		if (initialTest != "good")
			return initialTest;
		if (!isValidBP(bloodPressure))
			return "Please enter Blood Pressure in the form 'integer / integer'";
		
		// all tests passed, create the Penguin and save to file.
		GPSTracker tracker = new GPSTracker (gpsa, gpsb, gpsc);
		Penguin penguin = new Penguin(name, gender, weight, bloodPressure, tracker);
		
		try {
			writer = new BufferedWriter(new FileWriter("animals/"+penguin.getFileName()));
			String[] lines = penguin.getDataLines();
			for (int idx=0; idx<lines.length; idx++) {
				writer.write(lines[idx]);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "Data not saved: "+e.getMessage();
		}
		
		return "Data Recorded Successfully to "+penguin.getFileName();
	}
	
	public String saveSeaLion(String name, String gender, String weight, String numOfSpots,
			String gpsa, String gpsb, String gpsc) {
		String initialTest = initialValidation(name, gender, weight, gpsa, gpsb, gpsc);
		if (initialTest != "good")
			return initialTest;
		if (!isValidNumOfSpots(numOfSpots))
			return "Please enter a positive integer number of spots.";
		
		// all tests passed, create the Sea Lion and save to file.
		GPSTracker tracker = new GPSTracker (gpsa, gpsb, gpsc);
		SeaLion sealion = new SeaLion(name, gender, weight, numOfSpots, tracker);
		
		try {
			writer = new BufferedWriter(new FileWriter("animals/"+sealion.getFileName()));
			String[] lines = sealion.getDataLines();
			for (int idx=0; idx<lines.length; idx++) {
				writer.write(lines[idx]);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "Data not saved: "+e.getMessage();
		}
		
		return "Data Recorded Successfully to "+sealion.getFileName();
	}
	
	public String saveWalrus(String name, String gender, String weight, String dentalHealth,
			String gpsa, String gpsb, String gpsc) {
		
		String initialTest = initialValidation(name, gender, weight, gpsa, gpsb, gpsc);
		if (initialTest != "good")
			return initialTest;
		if (!isValidDentalHealth(dentalHealth))
			return "Please select Dental Health level from the drop-down.";
		
		// all tests passed, create the Penguin and save to file.
		GPSTracker tracker = new GPSTracker (gpsa, gpsb, gpsc);
		Walrus walrus = new Walrus(name, gender, weight, dentalHealth, tracker);
		
		try {
			writer = new BufferedWriter(new FileWriter("animals/"+walrus.getFileName()));
			String[] lines = walrus.getDataLines();
			for (int idx=0; idx<lines.length; idx++) {
				writer.write(lines[idx]);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "Data not saved: "+e.getMessage();
		}
		
		return "Data Recorded Successfully to "+walrus.getFileName();
	}
	
	// validation methods
	
	// validation required for all animal subtypes.  Returns 'good' for valid input
	// or a specific error message for invalid input.
	private String initialValidation(String name, String gender, String weight,
			String gpsa, String gpsb, String gpsc) {
		if (!isValidName(name))
			return "Please use only letter, spaces, and - or ' in the Animal Name.";
		if (!isValidGender(gender))
			return "Please select a gender.";
		if (!isValidDecimal(weight))
			return "Please enter a positive decimal or integer for the weight.";
		if (!isValidGPS(gpsa) || !isValidGPS(gpsb) || !isValidGPS(gpsc))
			return "Gps coordinates must be blank, or 'latitude, longitude'"
					+ " with latitude between -90 and 90 and longitude from -180 to 180.";		
		return "good";
	}
	
	private boolean isValidName(String name) {
		return Pattern.matches("[a-zA-Z]+[-a-zA-Z ']*", name);
	}
	
	private boolean isValidDecimal(String weight) {
		return Pattern.matches("[1-9]+[0-9]*", weight) ||
				Pattern.matches("[1-9]+[0-9]*\\.[0-9]+", weight) ||
				Pattern.matches("0\\.[0-9]+", weight);
	}
	
	private boolean isValidSignedDecimal(String dec) {
		if (dec.charAt(0) == "-".charAt(0))
			return isValidDecimal(dec.substring(1));
		else
			return isValidDecimal(dec);
	}
	
	private boolean isValidGender(String gen) {
		return gen == "Male" || gen == "Female";
	}
	
	private boolean isValidBP(String pressure) {
		return Pattern.matches("[0-9]+\\s*/\\s*[0-9]+", pressure);
	}
	
	private boolean isValidNumOfSpots(String spots) {
		return Pattern.matches("[1-9]+[0-9]*", spots);
	}
	
	private boolean isValidDentalHealth(String hlth) {
		return hlth == "Poor" || hlth == "Average" || hlth == "Good";
	}
	
	private boolean isValidGPS(String coords) {
		// Allow empty Strings
		if (coords.isEmpty())
			return true;
		
		// Check for split into two parts.
		String[] coordArray = coords.split("\\s*,\\s*");
		if (coordArray.length != 2)
			return false;
		
		// Check each part is a signed decimal
		for (int idx=0; idx<2; idx++) 
			if (!isValidSignedDecimal(coordArray[idx]))
					return false;
		
		// Check for proper latitude and longitude bounds
		double lat = Double.parseDouble(coordArray[0]);
		if (lat < -90 || lat > 90)
			return false;
		double lon = Double.parseDouble(coordArray[1]);
		if (lon < -180 || lon > 180)
			return false;
		
		// If still here, all tests passed!
		return true;
	}
}
