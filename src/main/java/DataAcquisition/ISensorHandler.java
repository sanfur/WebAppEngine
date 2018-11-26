package DataAcquisition;

import java.util.ArrayList;
import DataAcquisition.SensorData.*;

public interface ISensorHandler {
	
	public void setListOfGeoSensors(ILocator locator);
	public ArrayList<GeoSensor> getListOfGeoSensors();
	public void execute();
}
