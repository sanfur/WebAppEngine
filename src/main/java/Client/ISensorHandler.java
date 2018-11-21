package Client;

import java.util.ArrayList;
import Client.SensorData.*;

public interface ISensorHandler {
	
	public void setListOfGeoSensors(ILocator locator);
	public void setListOfSensors(ILocator locator);
	
	public ArrayList<GeoSensor> getListOfGeoSensors();
	public ArrayList<Sensor> getListOfSensors();
}
