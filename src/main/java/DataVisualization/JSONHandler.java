package DataVisualization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Entity;

public class JSONHandler {
	
	public void createCoordinatesObject(String filePath, List<Entity> coordinateEntities) throws JSONException, IOException {
			
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < coordinateEntities.size(); i++) {
			JSONObject sensObj = new JSONObject();
			sensObj.put("longitude", coordinateEntities.get(i).getProperty("Longitude").toString());
			sensObj.put("latitude", coordinateEntities.get(i).getProperty("Latitude").toString());
			sensObj.put("sensorID", coordinateEntities.get(i).getProperty("SensorID").toString());
			jsonArray.put(sensObj);
		}
		jsonObj.put("coordinates", jsonArray);

		//TODO: change absolute to relative path
		try (FileWriter file = new FileWriter(filePath)) {
			file.write(jsonObj.toString());
			System.out.println("SUCCESS JSONHandler: Copied JSON Coordinates Object to File, / Object: " + jsonObj);
		}
		catch(IOException e) {
		e.printStackTrace();
		}			
	}
	
	public void createMeasurementObject(String filePath, List<Entity> temperatureEntities) throws JSONException, IOException {
		
		JSONObject jsonObj = new JSONObject();
		JSONArray tempArray = new JSONArray();
		for(int i = 0; i < temperatureEntities.size(); i++) {
			JSONObject tempObj = new JSONObject();
			tempObj.put("numberOfMeasurements", temperatureEntities.get(i).getProperty("NumberOfMeasurements").toString());
			tempObj.put("sensorID", temperatureEntities.get(i).getProperty("SensorID").toString());
			tempObj.put("time", temperatureEntities.get(i).getProperty("Timestamp").toString());
			tempObj.put("temp", temperatureEntities.get(i).getProperty("Temperature").toString());
			tempArray.put(tempObj);
		}
		jsonObj.put("temperature", tempArray);

		//TODO: change absolute to relative path
		try (FileWriter file = new FileWriter(filePath)) {
			file.write(jsonObj.toString());
			System.out.println("SUCCESS JSONHandler: Copied JSON Temperature Object to File, / Object: " + jsonObj);
		}
		catch(IOException e) {
		e.printStackTrace();
		}			
	}
}
