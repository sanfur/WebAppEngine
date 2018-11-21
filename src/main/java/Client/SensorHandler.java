package Client;

import java.util.ArrayList;

import Client.SensorData.*;

public class SensorHandler implements ISensorHandler{

	private ILocator locator;
	
	public SensorHandler(ILocator locator) {
		this.locator = locator;
	}
	
	public ArrayList<GeoSensor> getListOfGeoSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Sensor> getListOfSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setListOfGeoSensors(ArrayList<GeoSensor> sensor, ILocator locator) {
		// TODO Auto-generated method stub
		
	}

	public void setListOfSensors(ArrayList<Sensor> sensor, ILocator locator) {
		// TODO Auto-generated method stub
		
	}	
}
