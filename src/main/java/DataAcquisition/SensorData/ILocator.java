package DataAcquisition.SensorData;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ILocator {
	public void setFilePath(String filePath);
	public ArrayList<Sensor> getSensorCoordinates() throws FileNotFoundException;
}
