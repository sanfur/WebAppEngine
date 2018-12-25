package Utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import DataAcquisition.ILocator;
import DataAcquisition.Sensor;

public class FileInterpreter {

	private ILocator locator;
	private ArrayList<Sensor> plainSensors;
	
	public FileInterpreter(ILocator locator) {
		this.locator = locator;
		plainSensors = new ArrayList<Sensor>();
	}
	
	public ArrayList<Sensor> getSensors(HttpServletRequest request) throws IOException, ServletException{
		
		// Get Textfile
 	    Part filePart = request.getPart("geoData"); 
 	    InputStream fileContent = filePart.getInputStream(); 		
 	    System.out.println("FileInterpreter: Content written: " + fileContent);
 	    
 	    // Make Sensors out of Textfile
	    plainSensors = locator.getSensorCoordinates(fileContent);		
	    System.out.println("FileInterpreter: Created Sensors: " + plainSensors.size());
	    
	    return plainSensors;
	}
}
