package DataAcquisition.SensorData;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ILocator {
	public ArrayList<Sensor> getSensorCoordinates(String dataFile) throws FileNotFoundException;
}
