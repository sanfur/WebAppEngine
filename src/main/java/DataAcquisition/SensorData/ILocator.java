package DataAcquisition.SensorData;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ILocator {
	public ArrayList<Coordinates> getCoordinates() throws FileNotFoundException;
}
