package DataVisualization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Entity;

public class JSONHandler {
	
	private List<Entity> coordinateEntities;
	private List<Entity> temperatureEntities;

	public JSONHandler(List<Entity> coordinateEntities, List<Entity> temperatureEntities) {
		this.coordinateEntities = coordinateEntities;
		this.temperatureEntities = temperatureEntities;
	}

	public void createCoordinatesObject(String filePath) throws JSONException, IOException {
			
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < coordinateEntities.size(); i++) {

			JSONObject sensObj = new JSONObject();
			sensObj.put("longitude", coordinateEntities.get(i).getProperty("Longitude").toString());
			sensObj.put("latitude", coordinateEntities.get(i).getProperty("Latitude").toString());
			sensObj.put("id", coordinateEntities.get(i).getProperty("ID").toString());
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
	
	public void createTemperaturesObject(String filePath, int numberOfMeasurements) throws JSONException, IOException {
		
		JSONObject jsonObj = new JSONObject();
		JSONArray tempArray = new JSONArray();
		for(int i = 0; i < temperatureEntities.size(); i++) {
			JSONObject tempObj = new JSONObject();
			tempObj.put("measurement_id", temperatureEntities.get(i).getProperty("Measurement_ID").toString());
			tempObj.put("temp_id", temperatureEntities.get(i).getProperty("ID").toString());
			tempObj.put("time", temperatureEntities.get(i).getProperty("Timestamp").toString());
			tempObj.put("temp", temperatureEntities.get(i).getProperty("Temperature").toString());
			tempArray.put(tempObj);
		}
		jsonObj.put("measurements", tempArray);

		//TODO: change absolute to relative path
		try (FileWriter file = new FileWriter(filePath)) {
			file.write(jsonObj.toString());
			System.out.println("SUCCESS JSONHandler: Copied JSON Temperature Object to File, / Object: " + jsonObj);
		}
		catch(IOException e) {
		e.printStackTrace();
		}			
	}
	
	public void deleteJSONObject(String filePath) {
		File file = new File(filePath);
		if(file.delete()) {
			System.out.println("SUCCESS JSONHandler: File sensorObjects.json deleted.");
		}
		else {
			System.out.println("FAILED JSONHandler: File sensorObjects.json doesn't exist.");
		}
	}
}
