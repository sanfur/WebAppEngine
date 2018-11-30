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

	public void execute(String dataFile) {
	    Locator locator = new Locator();
	    setListOfGeoSensors(locator, dataFile);
	}
	
	public void setListOfGeoSensors(ILocator locator, String dataFile) {
		try {
			
			int addedSensors = 0;
			
			coordinates = locator.getCoordinates(dataFile);
			for(int i = 0; i < coordinates.size(); i++) {
				
				TempDataHandler handl = new TempDataHandler();
				
				GeoSensor sensor = new GeoSensor(i, handl.GetTempData(), coordinates.get(i));							
				sensors.add(sensor);
				addedSensors++;
			}
			
			// TODO Testing (SensorHandler) Sensor added to GeoSensorList
			System.out.println("SensorHandler: Added Sensors to GeoSensorList: " + addedSensors);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void collectAllTemperatureData() {
		// TODO Auto-generated method stub
		
	}
}
