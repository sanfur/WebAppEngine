package DataAcquisition;

import java.util.ArrayList;
import DataAcquisition.SensorData.*;

public interface ISensorHandler {
	
	public void collectAllTemperatureData();
	public void setListOfGeoSensors(ILocator locator, String dataFile);
	public ArrayList<GeoSensor> getListOfGeoSensors();
	public void execute(String dataFile);
}
