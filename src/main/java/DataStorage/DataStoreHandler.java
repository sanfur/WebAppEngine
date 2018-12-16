package DataStorage;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.*;
import DataAcquisition.SensorData.*;

public class DataStoreHandler {
	
	private ArrayList<Key> coordinatesKeys;
	private ArrayList<Key> temperaturesKeys;
	private int measurementsInStore = 0;
	private int sensorsInDataStore = 0;
	private int numberOfMeasurementEntities = 0;
	
	public DataStoreHandler() {
		coordinatesKeys = new ArrayList<Key>();
		temperaturesKeys = new ArrayList<Key>();
	}

	public void saveSensorToDataStore(Sensor sensor) {
		sensorsInDataStore++;
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key sensorKey = KeyFactory.createKey("Coordinates",sensorsInDataStore);
		Entity currentSensor = new Entity(sensorKey);
		coordinatesKeys.add(sensorKey);
    	currentSensor.setProperty("SensorID", sensor.getSensorID());
    	currentSensor.setProperty("Latitude", sensor.getLat());
    	currentSensor.setProperty("Longitude", sensor.getLong());
    	datastore.put(currentSensor);
	}
	
	public void saveTemperaturesToDataStore(MeasurementSensor sensor) {
		measurementsInStore++;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key sensorKey = KeyFactory.createKey("Measurements", measurementsInStore);
		Entity currentSensor = new Entity(sensorKey);
		temperaturesKeys.add(sensorKey);
    	currentSensor.setProperty("NumberOfMeasurements", measurementsInStore);
    	currentSensor.setProperty("SensorID", sensor.getSensorID());
    	currentSensor.setProperty("Temperature", sensor.getTemperature());
    	currentSensor.setProperty("Timestamp", sensor.getTimeStamp());
    	datastore.put(currentSensor);
	}
	
	public List<Entity> getCoordinatesFromDataStore() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();		
		Query query = new Query("Coordinates");
		PreparedQuery preparedQuery = dataStore.prepare(query);
		List<Entity> list = preparedQuery.asList(FetchOptions.Builder.withDefaults());	
		return list;		
	}
	
	public List<Entity> getTemperaturesFromDataStore() {
		
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();		
		Query query = new Query("Measurements");
		PreparedQuery preparedQuery = dataStore.prepare(query);
		List<Entity> list = preparedQuery.asList(FetchOptions.Builder.withDefaults());	
		return list;
	}
	
	public void deleteDataStore() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		
		// Delete Coordiantes		
		Query queryCoordinates = new Query("Coordinates");
		PreparedQuery preparedQueryCoordinates = dataStore.prepare(queryCoordinates);
		List<Entity> coordinates = preparedQueryCoordinates.asList(FetchOptions.Builder.withDefaults());
		coordinates.forEach(entity -> {dataStore.delete(entity.getKey());});
		
		// Delete Measurements		
		Query queryMeasurements = new Query("Measurements");
		PreparedQuery aredQueryMeasurements = dataStore.prepare(queryMeasurements);
		List<Entity> measurements = aredQueryMeasurements.asList(FetchOptions.Builder.withDefaults());
		measurements.forEach(entity -> {dataStore.delete(entity.getKey());});
		
		System.out.println("SUCCESS DataStoreHandler: Datastore cleared.");
	}
}
