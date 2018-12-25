package DataAcquisition;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public interface ILocator {
	public ArrayList<Sensor> getSensorCoordinates(InputStream inputStream) throws FileNotFoundException;
}
