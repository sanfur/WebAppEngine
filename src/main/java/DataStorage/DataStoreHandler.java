package DataStorage;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.*;
import DataAcquisition.SensorData.*;

public class DataStoreHandler {
	
	private ArrayList<GeoSensor> sensors;
	private ArrayList<Key> coordinatesKeys;
	private ArrayList<Key> temperaturesKeys;
	private int measurementsInStore = 0;
	private int numberOfMeasurementEntities = 0;
	public DataStoreHandler(ArrayList<GeoSensor> sensors) {
		this.sensors = sensors;
		coordinatesKeys = new ArrayList<Key>();
		temperaturesKeys = new ArrayList<Key>();
	}

	public void saveCoordinatesToDataStore() {
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		for(GeoSensor sensor : sensors) {
			Key sensorKey = KeyFactory.createKey("Coordinates",sensor.getID());
			Entity currentSensor = new Entity(sensorKey);
			coordinatesKeys.add(sensorKey);
	    	currentSensor.setProperty("ID", sensor.getID());
	    	currentSensor.setProperty("Latitude", sensor.getCoordinates().getLat());
	    	currentSensor.setProperty("Longitude", sensor.getCoordinates().getLong());
	    	datastore.put(currentSensor);
		}		
	}
	
	public void saveTemperaturesToDataStore(GeoSensor sensor, int numberOfMeasurement) {
		numberOfMeasurementEntities++;
		if(measurementsInStore < numberOfMeasurement) {
			measurementsInStore = numberOfMeasurement;
		}
		
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key sensorKey = KeyFactory.createKey("Measurements", numberOfMeasurementEntities);
		Entity currentSensor = new Entity(sensorKey);
		temperaturesKeys.add(sensorKey);
    	currentSensor.setProperty("Measurement_ID", numberOfMeasurementEntities);
    	currentSensor.setProperty("ID", sensor.getID());
    	currentSensor.setProperty("Temperature", sensor.getTempWithTime().getTemperature());
    	currentSensor.setProperty("Timestamp", sensor.getTempWithTime().getTimeStamp());
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
	
	public void deleteDataStore(String entityKind) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		Query query = new Query(entityKind);
		PreparedQuery preparedQuery = dataStore.prepare(query);
		List<Entity> list = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		list.forEach(entity -> {dataStore.delete(entity.getKey());});
		System.out.println("SUCCESS DataStoreHandler: Datastore cleared.");
	}
}
