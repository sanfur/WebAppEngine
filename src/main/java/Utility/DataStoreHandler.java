package Utility;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

import DataAcquisition.MeasurementSensor;
import DataAcquisition.Sensor;

public class DataStoreHandler {
		
	public DataStoreHandler() {
	}
	
	public List<Entity> getKindFromDatastore(String kind, DatastoreService datastoreService){
		Query query = new Query(kind);
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		return preparedQuery.asList(FetchOptions.Builder.withDefaults());
	}
	
	public void deleteEntitiesOfKind(String kind, DatastoreService datastoreService) {
		Query query = new Query(kind);
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		entities.forEach(entity -> {datastoreService.delete(entity.getKey());});
	}
	
	public void putSensorsToDatastore(ArrayList<Sensor> plainSensors, DatastoreService datastoreService) {
		ArrayList<Key> coordinatesKeys = new ArrayList<Key>();
	    int sensorsInDataStore = 0;
	    for(Sensor sensor : plainSensors) {
			sensorsInDataStore++;
			Key sensorKey = KeyFactory.createKey("Coordinates",sensorsInDataStore);
			Entity currentSensor = new Entity(sensorKey);
			coordinatesKeys.add(sensorKey);
	    	currentSensor.setProperty("SensorID", sensor.getSensorID());
	    	currentSensor.setProperty("Latitude", sensor.getLat());
	    	currentSensor.setProperty("Longitude", sensor.getLong());
	    	datastoreService.put(currentSensor);
	    }
	}
	
	public void putJsonToDatastore(int key, String propertyValue, DatastoreService datastoreService) {
		Key coordinatesJSONKey = KeyFactory.createKey("JSON",key);
		Text jsonText = new Text(propertyValue);
		Entity jsonEntity = new Entity(coordinatesJSONKey);
		jsonEntity.setProperty("object", jsonText);
    	datastoreService.put(jsonEntity);
	}
	
	public String getJsonFromDatastore(int key, DatastoreService datastoreService) throws EntityNotFoundException {
		Key jsonKey = KeyFactory.createKey("JSON", key);
		Entity entity = datastoreService.get(jsonKey);
		Text text = (Text)entity.getProperty("object");
		return text.getValue();
	}
	
	public int putMeasurementsToDatastore(int measurementsInDatastore, ArrayList<MeasurementSensor> measurementSensors, DatastoreService datastoreService) {
	    ArrayList<Key> temperaturesKeys = new ArrayList<Key>();
		for(MeasurementSensor sensor : measurementSensors) {
			measurementsInDatastore++;
			Key sensorKey = KeyFactory.createKey("Measurements", measurementsInDatastore);
			Entity currentSensor = new Entity(sensorKey);
			temperaturesKeys.add(sensorKey);
	    	currentSensor.setProperty("NumberOfMeasurements", measurementsInDatastore);
	    	currentSensor.setProperty("SensorID", sensor.getSensorID());
	    	currentSensor.setProperty("Temperature", sensor.getTemperature());
	    	currentSensor.setProperty("Timestamp", sensor.getTimeStamp());
	    	datastoreService.put(currentSensor);
		}
		return measurementsInDatastore;
	}
}
