package Client;

import java.util.ArrayList;
import Client.SensorData.*;

public interface ISensorHandler {
	
	public void setListOfGeoSensors(ArrayList<GeoSensor> sensor, ILocator locator);
	public void setListOfSensors(ArrayList<Sensor> sensor, ILocator locator);
	
	public ArrayList<GeoSensor> getListOfGeoSensors();
	public ArrayList<Sensor> getListOfSensors();
}
