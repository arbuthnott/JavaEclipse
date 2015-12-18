
public class GPSTracker {
	
	// properties
	// in stub version only allow 3 coordinates.
	private String[] locations = new String[3];
	
	public GPSTracker(String gpsa, String gpsb, String gpsc) {
		locations[0] = gpsa;
		locations[1] = gpsb;
		locations[2] = gpsc;
	}
	
	public String getCoords(int idx) {
		if (locations[idx].isEmpty())
			return "no data";
		else
			return locations[idx];
	}
}
