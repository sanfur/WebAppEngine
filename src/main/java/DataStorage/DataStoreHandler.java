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
		
		if(measurementsInStore < numberOfMeasurement) {
			measurementsInStore = numberOfMeasurement;
		}
		
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key sensorKey = KeyFactory.createKey("Measurement_" + numberOfMeasurement, sensor.getID());
		Entity currentSensor = new Entity(sensorKey);
		temperaturesKeys.add(sensorKey);
    	currentSensor.setProperty("ID", sensor.getID());
    	currentSensor.setProperty("Temperature", sensor.getTempWithTime().getTemperature());
    	currentSensor.setProperty("Timestamp", sensor.getTempWithTime().getTimeStamp());
    	datastore.put(currentSensor);
	}
	
	public List<Entity> getCoordinatesFromDataStore() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		Query query = new Query("Coordinates");
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> list = preparedQuery.asList(FetchOptions.Builder.withDefaults());	
		return list;		
	}
	
	public ArrayList<List<Entity>> getTemperaturesFromDataStore() {
		ArrayList<List<Entity>> fullList = new ArrayList<List<Entity>>();
		for(int i = 0; i < measurementsInStore; i++) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
			Query query = new Query("Measurement_"+ (i + 1));
			PreparedQuery preparedQuery = datastore.prepare(query);
			List<Entity> list = preparedQuery.asList(FetchOptions.Builder.withDefaults());
			fullList.add(list);
		}
		return fullList;
	}
	
	public void deleteDataStore() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		for(Key sensorKey : coordinatesKeys) {
			datastore.delete(sensorKey);
		}
		for(Key sensorKey : temperaturesKeys) {
			datastore.delete(sensorKey);
		}		
		System.out.println("SUCCESS DataStoreHandler: Datastore cleared.");
	}
}
