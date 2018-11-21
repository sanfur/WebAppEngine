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

	public void setListOfGeoSensors(ILocator locator) {
		
	}

	public void setListOfSensors(ILocator locator) {
		// TODO Auto-generated method stub
		
	}	
}
