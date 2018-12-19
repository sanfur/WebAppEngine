package DataAcquisition.SensorData;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import DataAcquisition.SensorData.ILocator;
import DataAcquisition.SensorData.Sensor;

public class ShowLocator implements ILocator{

	public ArrayList<Sensor> getSensorCoordinates() throws FileNotFoundException {
		ArrayList<Sensor> geoLocations = new ArrayList<Sensor>();
		Sensor sensorA = new Sensor(1, 46.84986, 9.53287);
		Sensor sensorB = new Sensor(2, 46.85986, 9.54287);
		Sensor sensorC = new Sensor(3, 46.86986, 9.55287);
		
		geoLocations.add(sensorA);
		geoLocations.add(sensorB);
		geoLocations.add(sensorC);
		return geoLocations;
	}

	public void setFilePath(String filePath) {
		// Nothing to do here
	}
}
