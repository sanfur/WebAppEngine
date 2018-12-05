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
	private ArrayList<List<Entity>> temperatureEntities;

	public JSONHandler(List<Entity> coordinateEntities, ArrayList<List<Entity>> temperatureEntities) {
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
		JSONObject tempObj = new JSONObject();
		JSONObject measureObj = new JSONObject();
		JSONArray tempArray = new JSONArray();
		JSONArray idArray = new JSONArray();
		for(int i = 0; i < numberOfMeasurements; i++) {
			int index = 0;
			for(List<Entity> entryList : temperatureEntities) {
				JSONObject idObj = new JSONObject();
				idObj.put("id", entryList.get(index).getProperty("ID").toString());
				idObj.put("time", entryList.get(index).getProperty("Timestamp").toString());
				idObj.put("temp", entryList.get(index).getProperty("Temperature").toString());
				tempArray.put(tempObj);
				idArray.put(idObj);
				index++;
			}
			measureObj.put("measurement" + (i + 1), idArray);
			tempArray.put(measureObj);
		}
		jsonObj.put("temperatures", tempArray);

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
