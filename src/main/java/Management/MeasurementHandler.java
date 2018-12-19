package Management;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import DataAcquisition.SensorData.MeasurementSensor;
import DataAcquisition.SensorData.Sensor;
import DataAcquisition.SensorData.Measurement.TempHandler;
import DataAcquisition.SensorData.Measurement.TimeStampCollector;
import DataStorage.DataStoreHandler;
import DataVisualization.JSONGenerator;

public class MeasurementHandler {

	private ArrayList<Sensor> plainSensors;
	private DataStoreHandler dataStoreHandler;
	private JSONGenerator jsonGenerator;

	public MeasurementHandler(ArrayList<Sensor> plainSensors, DataStoreHandler dataStoreHandler, JSONGenerator jsonGenerator) {
		this.plainSensors = plainSensors;
		this.dataStoreHandler = dataStoreHandler;
		this.jsonGenerator = jsonGenerator;
	}
	
	public void prepareMeasurements(TimeStampCollector timeStampCollector, TempHandler tempHandler, String temperaturesJSONFile) throws IOException {
		
		// Generate Measurements
    	ArrayList<MeasurementSensor> measurementSensors = new ArrayList<MeasurementSensor>();
		for(Sensor sensor : plainSensors) {
		    // Create Measurement
	    	long timeStamp = timeStampCollector.getTimeStamp();
	    	int temperature = tempHandler.getRandomTemp();
	    	MeasurementSensor measurementSensor = new MeasurementSensor(
	    			sensor.getSensorID(), 
	    			sensor.getLat(), 
	    			sensor.getLong(), 
	    			timeStamp, 
	    			temperature
			);
	    	measurementSensors.add(measurementSensor);
			System.out.println("AppEngineServlet: Added Measurements to Sensor: " + sensor.getSensorID());
	    }
		
		// Put Measurements to Datastore
		for(MeasurementSensor sensor : measurementSensors) {
			dataStoreHandler.saveTemperaturesToDataStore(sensor);
		}
		System.out.println("AppEngineServlet: Added Measurements to Datastore");
		
		// Create JSON-Object for Sensors
		try {
			jsonGenerator.createMeasurementObject(temperaturesJSONFile, dataStoreHandler.getTemperaturesFromDataStore());
		} catch (JSONException e) { e.printStackTrace(); }	
		System.out.println("AppEngineServlet: Generated Measurement JSON-Object");
	}
}
