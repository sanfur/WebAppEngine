package Client.SensorData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocatorDB implements ILocator{
	
	public Map<Double, Double> getCoordinates() throws FileNotFoundException {
		
		Map<Double, Double> geoLocations = new HashMap<Double, Double>();
		Scanner scanner = new Scanner(new File("D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\input\\DB.txt"));
		
		while(scanner.hasNextLine()) {
			String xAndYAxis = scanner.nextLine();
			
			// check for 1 to 3 digits, 
			// check for "." or nothing,
			// check for digits until ","
			// chack for 1 to 3 digits,
			// check for "." or nothing,
			// check ford digits until end			
			Pattern regex = Pattern.compile("(\\d{1,3}\\.?\\d*),(\\d{1,3}\\.?\\d*)");
			Matcher regexMatcher = regex.matcher(xAndYAxis);
			
			if(regexMatcher.matches()) {
				double lat = Double.parseDouble(regexMatcher.group(1));
				double lon = Double.parseDouble(regexMatcher.group(2));
				
				geoLocations.put(lat, lon);
			}		
		}		
		scanner.close();		
		return geoLocations;
		
//		Map<Double, Double> geoLocations = new HashMap<Double, Double>();
//		Scanner scanner = new Scanner(new File("D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\input\\DB.txt"));
//		
//		while(scanner.hasNextLine()) {
//			String xAndYAxis = scanner.nextLine();
//			
//			// check for 1 to 3 digits, 
//			// check for "." or nothing,
//			// check for digits until ","
//			// chack for 1 to 3 digits,
//			// check for "." or nothing,
//			// check ford digits until end			
//			Pattern regex = Pattern.compile("(\\d{1,3}\\.?\\d*),(\\d{1,3}\\.?\\d*)");
//			Matcher regexMatcher = regex.matcher(xAndYAxis);
//			
//			if(regexMatcher.matches()) {
//				double lat = Double.parseDouble(regexMatcher.group(1));
//				double lon = Double.parseDouble(regexMatcher.group(2));
//				
//				geoLocations.put(lat, lon);
//			}		
//		}		
//		scanner.close();
//		
//		for(int i = 0; i < geoLocations.size(); i++) {
//			System.out.println(geoLocations.get);
//		}
		
	}
}
