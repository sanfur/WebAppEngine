package Client.SensorData;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocatorDB implements ILocator{

	private int[][] coordinates;
	
	public int[][] getCoordinates() throws FileNotFoundException {
		
		//Read .txt
//		Scanner scanner = new Scanner(new File("D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\input\\DB.txt"));
//		String xAndYAxis = scanner.nextLine();
		String xAndYAxis = "34,56";
		
		Pattern pattern = Pattern.compile("(\\d{1,2})");
		Matcher matcher = pattern.matcher(xAndYAxis);
		System.out.println(matcher.start() + " , " + matcher.end());
//		scanner.close();
		
		return null;
	}
}
