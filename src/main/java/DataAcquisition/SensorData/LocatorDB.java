package DataAcquisition.SensorData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocatorDB implements ILocator{
	
	public ArrayList<Coordinates> getCoordinates() throws FileNotFoundException {
		
		ArrayList<Coordinates> geoLocations = new ArrayList<Coordinates>();
		//TODO: change absolute to relative path
		Scanner scanner = new Scanner(new File("D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\input\\DB.txt"));
		// "D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\input\\DB.txt"
		int line = 0;

		
		while(scanner.hasNextLine()) {
			String xAndYAxis = scanner.nextLine();			
			// check for 1 to 3 digits, 
			// check for "." or nothing,
			// check for digits until ","
			// check for 1 to 3 digits,
			// check for "." or nothing,
			// check ford digits until end			
			Pattern regex = Pattern.compile("(\\d{1,3}\\.?\\d*),(\\d{1,3}\\.?\\d*)");
			Matcher regexMatcher = regex.matcher(xAndYAxis);
			
			line++;
			if(regexMatcher.matches()) {			
				Coordinates coordinates = new Coordinates();
				coordinates.setLat(Double.parseDouble(regexMatcher.group(2)));
				coordinates.setLong(Double.parseDouble(regexMatcher.group(1)) * 1.5);									
				geoLocations.add(coordinates);

			}	
			else {				
				//TODO Testing File searched failed
				System.out.println("FAILED LocatorDB: Adding coordinates on line " + line);
			}
		}		
		scanner.close();
		
		// TODO Testing (LocatorDB) Coordinates added to GeoLocationList
		System.out.println("LocatorDB: Added Coordinates: " + geoLocations.size());
		
		return geoLocations;
	}
}
