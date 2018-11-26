package DataAcquisition;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import DataAcquisition.SensorData.*;

public class SensorHandler implements ISensorHandler{

	private ArrayList<Coordinates> coordinates;
	private ArrayList<GeoSensor> sensors;
	
	public SensorHandler() {
		sensors = new ArrayList<GeoSensor>();
	}
	
	public ArrayList<GeoSensor> getListOfGeoSensors() {
		return sensors;
	}

	public void execute() {
	    LocatorDB locator = new LocatorDB();
	    setListOfGeoSensors(locator);
	}
	
	public void setListOfGeoSensors(ILocator locator) {
		try {
			
			int addedSensors = 0;
			
			coordinates = locator.getCoordinates();
			for(int i = 0; i < coordinates.size(); i++) {
				GeoSensor sensor = new GeoSensor(i, 20, 30, coordinates.get(i));							
				sensors.add(sensor);
				addedSensors++;
			}
			
			// TODO Testing (SensorHandler) Sensor added to GeoSensorList
			System.out.println("SensorHandler: Added Sensors to GeoSensorList: " + addedSensors);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
