package Management;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import DataAcquisition.SensorData.*;
import DataStorage.DataStoreHandler;
import DataVisualization.JSONGenerator;

public class SensorHandler {

	private Locator locator;
	private DataStoreHandler dataStoreHandler;
	private JSONGenerator jsonGenerator;
	
	public SensorHandler(Locator locator, DataStoreHandler dataStoreHandler, JSONGenerator jsonGenerator) {
		this.locator = locator;
		this.dataStoreHandler = dataStoreHandler;
		this.jsonGenerator = jsonGenerator;
	}
	
	public ArrayList<Sensor> prepareSensors(String coordinatesFile, String coordinatesJSONFile) throws IOException {
		
		//	Create Sensors
		ArrayList<Sensor> plainSensors = new ArrayList<Sensor>();
	    plainSensors = locator.getSensorCoordinates(coordinatesFile);
		System.out.println("SensorHandler: Created Sensors: " + plainSensors.size());

		// Put Sensors to Datastore
		for(Sensor sensor : plainSensors) {
			dataStoreHandler.saveSensorToDataStore(sensor);
		}		
		System.out.println("SensorHandler: Added Sensors to Datastore");

		// Create JSON-Object for Sensors	
		try {
			jsonGenerator.createCoordinatesObject(coordinatesJSONFile, dataStoreHandler.getCoordinatesFromDataStore());
		} catch (JSONException e1) { e1.printStackTrace(); }
		System.out.println("SensorHandler: Generated Coordinate JSON-Object");
		
		return plainSensors;
	}
}
