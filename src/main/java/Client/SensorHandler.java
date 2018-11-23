package Client;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import Client.SensorData.*;

public class SensorHandler implements ISensorHandler{

	private ArrayList<Coordinates> coordinates;
	
	public SensorHandler(ArrayList<Coordinates> coordinates) {
		
	}
	
	public ArrayList<GeoSensor> getListOfGeoSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Sensor> getListOfSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setListOfGeoSensors(ILocator locator) {
		try {
			coordinates = locator.getCoordinates();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setListOfSensors(ILocator locator) {
		// TODO Auto-generated method stub
		
	}	
}
